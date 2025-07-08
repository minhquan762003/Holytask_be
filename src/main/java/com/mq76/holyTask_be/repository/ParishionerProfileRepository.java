package com.mq76.holyTask_be.repository;

import com.mq76.holyTask_be.model.ParishionerProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ParishionerProfileRepository extends JpaRepository<ParishionerProfile, Integer> {
//    Optional<ParishionerProfile> findByUser_Id(Integer userId);
    List<ParishionerProfile> findBySubParish_Id(Integer subParishId);
    Optional<ParishionerProfile> findByPhoneNumber(String phoneNumber);

    @Query(value = "SELECT * " +
            "FROM parishioner_profiles " +
            "WHERE sub_parish_id IN ( " +
            "    SELECT id " +
            "    FROM sub_parishes " +
            "    WHERE parish_id = :parishId " +
            ") order by sub_parish_id",
            nativeQuery = true)
    List<ParishionerProfile> findByParishId(@Param("parishId") Integer parishId);


    @Query(value = "SELECT * " +
            "FROM parishioner_profiles " +
            "WHERE parishioner_profiles.sub_parish_id IN ( " +
            "    SELECT id " +
            "    FROM sub_parishes " +
            "    WHERE parish_id = :parishId " +
            ")and parishioner_profiles.parish_group_id = :groupId order by  created_at",
            nativeQuery = true)
    List<ParishionerProfile> findByParishIdAndGroupId(Integer parishId, Integer groupId);

    @Query(value = "SELECT * " +
            "FROM parishioner_profiles " +
            "WHERE sub_parish_id IN (SELECT id FROM sub_parishes WHERE parish_id = :parishId) " +
            "AND LOWER(full_name) LIKE LOWER(CONCAT('%', :name, '%')) " +
            "ORDER BY created_at", nativeQuery = true)
    List<ParishionerProfile> findByNameAndParishId(@Param("parishId") Integer parishId, @Param("name") String name);


    @Query(value = "SELECT * " +
            "FROM parishioner_profiles " +
            "WHERE sub_parish_id IN ( " +
            "    SELECT id FROM sub_parishes WHERE parish_id = :parishId " +
            ") " +
            "AND parish_group_id = :groupId " +
            "AND LOWER(full_name) LIKE LOWER(CONCAT('%', :name, '%')) " +
            "ORDER BY created_at",
            nativeQuery = true)
    List<ParishionerProfile> searchParishionersParishIdGroupIdName(
            @Param("parishId") Integer parishId,
            @Param("groupId") Integer groupId,
            @Param("name") String name);


    @Query(value = "select * from parishioner_profiles where sub_parish_id in (select id from sub_parishes where parish_id = :parishId) order by view_date desc ",nativeQuery = true)
    List<ParishionerProfile> findByParishIdOrderByViewDateDesc(@Param("parishId") Integer parishId);


}
