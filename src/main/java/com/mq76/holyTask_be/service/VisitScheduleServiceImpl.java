package com.mq76.holyTask_be.service;

import com.mq76.holyTask_be.model.MessageConstants;
import com.mq76.holyTask_be.model.NotificationDTO;
import com.mq76.holyTask_be.model.ResponseObject;
import com.mq76.holyTask_be.model.VisitSchedule;
import com.mq76.holyTask_be.repository.PriestProfileRepository;
import com.mq76.holyTask_be.repository.VisitScheduleRepository;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import com.sendgrid.helpers.mail.objects.Personalization;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
@RequiredArgsConstructor
public class VisitScheduleServiceImpl implements VisitScheduleService {

    private final VisitScheduleRepository repository;

    @Override
    public ResponseObject createScheduleVisit(VisitSchedule visit) {
        try {
            visit.setNotes(visit.getNotes().trim());
            visit.setStatus(1);
            visit.setDatetime(visit.getDatetime());
            visit.setCreatedAt(new Date());
            repository.save(visit);
            return new ResponseObject(MessageConstants.OK, MessageConstants.THANH_CONG, visit);
        } catch (Exception e) {
            return new ResponseObject(MessageConstants.FAILED, MessageConstants.THAT_BAI, null);
        }
    }


    @Override
    public ResponseObject getVisitsByPriestId(Integer priestId) {
        try {
            repository.findByPriest_Id(priestId);
            return new ResponseObject(MessageConstants.OK, MessageConstants.THANH_CONG, repository.findByPriest_Id(priestId));
        } catch (Exception e) {
            return new ResponseObject(MessageConstants.FAILED, MessageConstants.THAT_BAI, null);
        }
    }

    @Override
    public ResponseObject findByDatetimeBetween(Date from, Date to) {
        try {
            repository.findByDatetimeBetween(from, to);
            return new ResponseObject(MessageConstants.OK, MessageConstants.THANH_CONG, repository.findByDatetimeBetween(from, to));
        } catch (Exception e) {
            return new ResponseObject(MessageConstants.FAILED, MessageConstants.THAT_BAI, null);
        }
    }


    @Override
    public ResponseObject updateScheduleVisit(VisitSchedule visit, Integer id) {
        if (repository.existsById(id)) {
            VisitSchedule updated = repository.findById(id).map(
                    schedule -> {
                        schedule.setAddress(visit.getAddress());
                        schedule.setUpdatedAt(new Date());
                        schedule.setCreatedUser(visit.getCreatedUser());
                        schedule.setVisitType(visit.getVisitType());
                        schedule.setHeadline(visit.getHeadline());
                        schedule.setNotes(visit.getNotes());
                        schedule.setDatetime(visit.getDatetime());
                        schedule.setUpdatedAt(new Date());
                        return repository.save(schedule);
                    }).orElseGet(() -> {

                return null;
            });
            return new ResponseObject(MessageConstants.OK, MessageConstants.THANH_CONG, updated);
        } else {
            return new ResponseObject(MessageConstants.FAILED, MessageConstants.THAT_BAI, null);
        }
    }

    @Override
    public ResponseObject findById(Integer id) {
        try {
            return new ResponseObject(MessageConstants.OK, MessageConstants.THANH_CONG, repository.findById(id));
        } catch (Exception e) {
            return new ResponseObject(MessageConstants.FAILED, MessageConstants.THAT_BAI, null);
        }
    }

    @Override
    public ResponseObject sendEmailNotification(Integer idVisit, String userEmail) {

        DateFormat df = new SimpleDateFormat("HH:mm dd/MM/yyyy");
        VisitSchedule visit = repository.findById(idVisit).get();
        String formatted = df.format(visit.getDatetime());

        String subject = "⏰ Sắp đến giờ: " + visit.getHeadline();
        String body = "Bạn có công việc \"" + visit.getHeadline()
                + "\" vào lúc "
                + formatted
                + " tại " + visit.getAddress();
        try {
            this.sendEmail(userEmail, subject, body);
            return new ResponseObject(MessageConstants.OK, MessageConstants.THANH_CONG, null);
        } catch (Exception e) {
            return new ResponseObject(MessageConstants.FAILED, MessageConstants.THAT_BAI, e);
        }
    }

    @Override
    public ResponseObject deleteScheduleVisit(Integer id) {
        try {
            repository.deleteById(id);
            return new ResponseObject(MessageConstants.OK, MessageConstants.THANH_CONG,id);
        }catch (Exception e){
            return new ResponseObject(MessageConstants.FAILED, MessageConstants.THAT_BAI, null);
        }
    }


    public List<VisitSchedule> findByPriestIdAndCurDate(Integer priestId) {
        try{
            Date currDate = new Date();
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            String strCurrDate = formatter.format(currDate);
            return repository.findByPriestIdAndDate(priestId, strCurrDate);
        }catch (Exception e){
            return null;
        }
    }


    @Value("${sendgrid.api-key}")
    private String apiKey;

    @Value("${sendgrid.from-email}")
    private String fromEmail;

    @Value("${sendgrid.from-name}")
    private String fromName;

    public void sendEmail(String to, String subject, String plainContent) throws IOException {
        // 1) Địa chỉ
        Email from    = new Email(fromEmail, fromName);
        Email toUser  = new Email(to);

        // 2) Nội dung HTML
        String htmlContent =
                """
                <p>%s</p>
                <p>🙏 Xin lưu ý đến lịch trình của bạn.</p>
                """.formatted(plainContent.replace("\n", "<br>"));

        // 3) Tạo mail
        Mail mail = new Mail();
        mail.setFrom(from);
        mail.setSubject(subject);

        Personalization p = new Personalization();
        p.addTo(toUser);
        mail.addPersonalization(p);

        // dual-content: text/plain + text/html
        mail.addContent(new Content("text/plain", plainContent));
        mail.addContent(new Content("text/html",  htmlContent));

        // 4) Header (dùng addHeader để tránh NullPointerException)
        mail.addHeader("X-Mailer",   "HolyTask Notification");
        mail.addHeader("X-Priority", "1");        // ưu tiên cao
        mail.addHeader("Precedence", "bulk");     // email hệ thống

        // 5) Gửi
        SendGrid sg   = new SendGrid(apiKey);
        Request req   = new Request();
        req.setMethod(Method.POST);
        req.setEndpoint("mail/send");
        req.setBody(mail.build());

        Response res  = sg.api(req);
        System.out.printf("STATUS: %d%nHEADERS: %s%n", res.getStatusCode(), res.getHeaders());

        if (res.getStatusCode() >= 400) {
            throw new RuntimeException("SendGrid error: " + res.getBody());
        }
    }

    @Autowired
    SimpMessagingTemplate messagingTemplate;

    public void sendScheduleNotification(Integer scheduleId,Integer priestId, String headline, String message) {
        VisitSchedule visitSchedule = repository.findById(scheduleId).get();
        NotificationDTO notification = new NotificationDTO(visitSchedule.getHeadline(), "Còn 5 phút nữa đến giờ công việc!", scheduleId);
        messagingTemplate.convertAndSend("/topic/schedule/" + priestId, notification);
    }

    // Lưu ID đã gửi để tránh gửi trùng
    private Set<Integer> notified = new HashSet<>();


    // Chạy mỗi phút
    @Scheduled(fixedRate = 60_000)
    public void checkAndNotifyUpcomingSchedules() {
        Date now = new Date();
        Date maxTime = new Date(now.getTime() + 5 * 60 * 1000); // now + 5 phút
        System.out.println("Bắt đầu kiểm tra lịch ");
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        String strCurrDate = formatter.format(now);

        List<VisitSchedule> schedulesToday = repository.findVisitByCurrentDate(strCurrDate);

        for (VisitSchedule schedule : schedulesToday) {
            Date scheduleTime = schedule.getDatetime();

//            if (scheduleTime.after(now) && scheduleTime.before(maxTime)) {
                if (!notified.contains(schedule.getId())) {
                    Integer priestId = schedule.getPriest().getId();

                    String headline = schedule.getHeadline();
                    String message = "Bạn có lịch vào lúc " + scheduleTime.toString();

                    this.sendScheduleNotification(schedule.getId(), priestId, headline, message);

                    notified.add(schedule.getId());
//                }
            }
        }
    }





}
