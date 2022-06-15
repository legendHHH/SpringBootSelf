package com.legend.springboot.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

@Entity
@Table(name = "nx_system_file_info")
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"})
public class NxSystemFileInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

	@Column(name = "key_word")
	private String key;

	@Column(name = "originName")
	private String originName;

	@Column(name = "fileName")
	private String fileName;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getOriginName() {
		return originName;
	}

	public void setOriginName(String originName) {
		this.originName = originName;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	@Override
	public String toString() {
		return "NxSystemFileInfo{" +
				"id=" + id +
				", originName='" + originName + '\'' +
				", fileName='" + fileName + '\'' +
				'}';
	}
}
