package com.abc.app.engine.common.util;

import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 分页数据
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class PageData<T> implements Serializable {

	private static final long serialVersionUID = 1L;

	private List<T> data;
	private Long count;

}
