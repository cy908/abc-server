package com.abc.app.engine.common.util;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 结果数据
 */
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
public class ResultData<T> implements Serializable {

	private static final long serialVersionUID = 1L;

	@NonNull
	private Boolean success;
	private String message;
	private T data;

}
