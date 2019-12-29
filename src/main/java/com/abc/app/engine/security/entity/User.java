package com.abc.app.engine.security.entity;

import java.io.Serializable;
import java.util.Date;

import com.abc.app.engine.common.util.EnableInfo;
import com.abc.app.engine.common.util.PageInfo;
import com.abc.app.engine.common.util.SearchInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 用户
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class User implements PageInfo, EnableInfo, SearchInfo, Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
	private Long departmentId;
	private String username;
	@JsonIgnore
	@ToString.Exclude
	private String password;

	private String name;
	private String code;
	private String title;
	private String card;

	private Integer gender;
	private Date birthday;
	private String email;
	private String phone;
	private String address;

	private Date createTime;
	private Date updateTime;

	private Boolean enable;
	private String note;

	// 扩展
	private Long pageIndex;
	private Long pageSize;
	private Department department;
	private Boolean checked;

	// 参数
	private String search;

}
