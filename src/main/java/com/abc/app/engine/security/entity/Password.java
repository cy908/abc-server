package com.abc.app.engine.security.entity;

import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 密码
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Password implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
	private List<Long> ids;
	@ToString.Exclude
	private String old;
	@ToString.Exclude
	private String now;

}
