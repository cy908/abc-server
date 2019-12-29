package com.abc.app.engine.common.entity;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 通知部门
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class NoticeDepartment implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long noticeId;
	private Long departmentId;

}
