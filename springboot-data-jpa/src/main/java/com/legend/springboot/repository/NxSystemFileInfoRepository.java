package com.legend.springboot.repository;

import com.legend.springboot.entity.NxSystemFileInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * XXX
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2022/5/3
 */
public interface NxSystemFileInfoRepository extends JpaRepository<NxSystemFileInfo, Integer> {

    @Query(value = "select * from nx_system_file_info f where f.key_word = ?1", nativeQuery = true)
    NxSystemFileInfo findNxSystemFileInfoByKey(@Param("key") String key);
}
