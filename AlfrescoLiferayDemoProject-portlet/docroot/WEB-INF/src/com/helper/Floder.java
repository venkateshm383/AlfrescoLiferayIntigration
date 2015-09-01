package com.helper;

import java.util.Date;
import java.util.Set;

public class Floder {
	String parentFloder;
	public String getParentFloder() {
		return parentFloder;
	}
	public void setParentFloder(String parentFloder) {
		this.parentFloder = parentFloder;
	}
	String floderName;
	String floderPath;
	String floderId;
	String fileName;
	String fileId;
	String rootFloder;
	String fileType;
	String fileCreatedBy;
	String filelLastModefiedBy;
	Date dateFileCreated;
	Date dateOfLastModefied;
	String version;
	Set versions;

	
	public Set getVersions() {
		return versions;
	}
	public void setVersions(Set versions) {
		this.versions = versions;
	}
	public String getFileType() {
		return fileType;
	}
	public void setFileType(String fileType) {
		this.fileType = fileType;
	}
	public String getFileCreatedBy() {
		return fileCreatedBy;
	}
	public void setFileCreatedBy(String fileCreatedBy) {
		this.fileCreatedBy = fileCreatedBy;
	}
	public String getFilelLastModefiedBy() {
		return filelLastModefiedBy;
	}
	public void setFilelLastModefiedBy(String filelLastModefiedBy) {
		this.filelLastModefiedBy = filelLastModefiedBy;
	}
	public Date getDateFileCreated() {
		return dateFileCreated;
	}
	public void setDateFileCreated(Date dateFileCreated) {
		this.dateFileCreated = dateFileCreated;
	}
	public Date getDateOfLastModefied() {
		return dateOfLastModefied;
	}
	public void setDateOfLastModefied(Date dateOfLastModefied) {
		this.dateOfLastModefied = dateOfLastModefied;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getRootFloder() {
		return rootFloder;
	}
	public void setRootFloder(String rootFloder) {
		this.rootFloder = rootFloder;
	}
	public String getFileId() {
		return fileId;
	}
	public void setFileId(String fileId) {
		this.fileId = fileId;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getFloderName() {
		return floderName;
	}
	public void setFloderName(String floderName) {
		this.floderName = floderName;
	}
	public String getFloderPath() {
		return floderPath;
	}
	public void setFloderPath(String floderPath) {
		this.floderPath = floderPath;
	}
	public String getFloderId() {
		return floderId;
	}
	public void setFloderId(String floderId) {
		this.floderId = floderId;
	}

}
