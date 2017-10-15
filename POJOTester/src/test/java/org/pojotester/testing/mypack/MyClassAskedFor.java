package org.pojotester.testing.mypack;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.pojotester.annotation.clazz.TestThisClass;

@TestThisClass
public class MyClassAskedFor {

	private int num;
	private String[] str;
	private LocalDateTime localDateTime;
	private LocalDate localDate;
	private LocalTime localTime;
	private Instant instant;
	private BigDecimal bigDecimal;
	private BigInteger bigInteger;
	private Set<String> set;
	private Map<String,String> map;
	private List<String> list;
	private java.util.Date utilDate;
	private java.sql.Date sqlDate;
	private java.util.Calendar calendar;
	
	private Boolean bool;
	private Byte byts;
	private Character charater;
	private Double doubleNum;
	private Float floatNum;
	private Integer intNum;
	private Long longNum;
	private Short shortNum;
	
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public String[] getStr() {
		return str;
	}
	public void setStr(String[] str) {
		this.str = str;
	}
	public LocalDateTime getLocalDateTime() {
		return localDateTime;
	}
	public void setLocalDateTime(LocalDateTime localDateTime) {
		this.localDateTime = localDateTime;
	}
	public LocalDate getLocalDate() {
		return localDate;
	}
	public void setLocalDate(LocalDate localDate) {
		this.localDate = localDate;
	}
	public LocalTime getLocalTime() {
		return localTime;
	}
	public void setLocalTime(LocalTime localTime) {
		this.localTime = localTime;
	}
	public Instant getInstant() {
		return instant;
	}
	public void setInstant(Instant instant) {
		this.instant = instant;
	}
	public BigDecimal getBigDecimal() {
		return bigDecimal;
	}
	public void setBigDecimal(BigDecimal bigDecimal) {
		this.bigDecimal = bigDecimal;
	}
	public BigInteger getBigInteger() {
		return bigInteger;
	}
	public void setBigInteger(BigInteger bigInteger) {
		this.bigInteger = bigInteger;
	}
	public List<String> getList() {
		return list;
	}
	public void setList(List<String> list) {
		this.list = list;
	}
	public Set<String> getSet() {
		return set;
	}
	public void setSet(Set<String> set) {
		this.set = set;
	}
	public Map<String, String> getMap() {
		return map;
	}
	public void setMap(Map<String, String> map) {
		this.map = map;
	}
	public java.util.Date getUtilDate() {
		return utilDate;
	}
	public void setUtilDate(java.util.Date utilDate) {
		this.utilDate = utilDate;
	}
	public java.sql.Date getSqlDate() {
		return sqlDate;
	}
	public void setSqlDate(java.sql.Date sqlDate) {
		this.sqlDate = sqlDate;
	}
	public java.util.Calendar getCalendar() {
		return calendar;
	}
	public void setCalendar(java.util.Calendar calendar) {
		this.calendar = calendar;
	}
	public Boolean getBool() {
		return bool;
	}
	public void setBool(Boolean bool) {
		this.bool = bool;
	}
	public Byte getByts() {
		return byts;
	}
	public void setByts(Byte byts) {
		this.byts = byts;
	}
	public Character getCharater() {
		return charater;
	}
	public void setCharater(Character charater) {
		this.charater = charater;
	}
	public Double getDoubleNum() {
		return doubleNum;
	}
	public void setDoubleNum(Double doubleNum) {
		this.doubleNum = doubleNum;
	}
	public Float getFloatNum() {
		return floatNum;
	}
	public void setFloatNum(Float floatNum) {
		this.floatNum = floatNum;
	}
	public Integer getIntNum() {
		return intNum;
	}
	public void setIntNum(Integer intNum) {
		this.intNum = intNum;
	}
	public Long getLongNum() {
		return longNum;
	}
	public void setLongNum(Long longNum) {
		this.longNum = longNum;
	}
	public Short getShortNum() {
		return shortNum;
	}
	public void setShortNum(Short shortNum) {
		this.shortNum = shortNum;
	}
}
