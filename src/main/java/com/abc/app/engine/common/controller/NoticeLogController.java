package com.abc.app.engine.common.controller;

import java.util.List;

import com.abc.app.engine.common.config.NoticeLogUrl;
import com.abc.app.engine.common.config.NoticeManageUrl;
import com.abc.app.engine.common.entity.NoticeLog;
import com.abc.app.engine.common.service.NoticeLogService;
import com.abc.app.engine.common.util.PageData;
import com.abc.app.engine.security.entity.Department;
import com.abc.app.engine.security.service.DepartmentService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 通知日志
 * 
 * @author 陈勇
 * @date 2018年8月8日
 *
 */
@RestController
@RequestMapping(NoticeLogUrl.URL_NOTICE)
public class NoticeLogController {

	@Autowired
	private NoticeLogService noticeLogService;

	@Autowired
	private DepartmentService departmentService;

	/**
	 * 通知日志列表
	 * 
	 * @param noticeLog
	 * @return
	 */
	@PostMapping(value = NoticeLogUrl.URL_NOTICE_LIST)
	@Secured(NoticeManageUrl.AUTH_NOTICE_LIST)
	public PageData<NoticeLog> noticeList(@RequestBody NoticeLog noticeLog) {
		long count = noticeLogService.findNoticeLogListCount(noticeLog);
		List<NoticeLog> list = null;
		if (count > 0) {
			list = noticeLogService.findNoticeLogList(noticeLog);
		}
		return new PageData<NoticeLog>(list, count);
	}

	/**
	 * 通知日志信息
	 * 
	 * @param noticeLog
	 * @return
	 */
	@PostMapping(value = NoticeLogUrl.URL_NOTICE_INFO)
	@Secured({ NoticeManageUrl.AUTH_NOTICE_INFO })
	public NoticeLog noticeInfo(@RequestBody NoticeLog noticeLog) {
		if (noticeLog.getLogId() == null) {
			noticeLog.setLogId(0L);
		}
		return noticeLogService.findNoticeLogByLogId(noticeLog.getLogId());
	}

	/**
	 * 部门列表
	 * 
	 * @return
	 */
	@GetMapping(value = NoticeLogUrl.URL_NOTICE_DEPARTMENTS)
	@Secured({ NoticeManageUrl.AUTH_NOTICE_LIST, NoticeManageUrl.AUTH_NOTICE_INFO })
	public List<Department> departmentList() {
		Department department = new Department();
		department.setEnable(true);// 查询启用的部门
		return departmentService.findDepartmentList(department);
	}

}
