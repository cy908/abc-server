package com.abc.app.engine.common.controller;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.abc.app.engine.common.config.NoticeUrl;
import com.abc.app.engine.common.entity.Notice;
import com.abc.app.engine.common.service.NoticeService;
import com.abc.app.engine.common.util.PageData;
import com.abc.app.engine.security.entity.User;
import com.abc.app.engine.security.service.UserService;
import com.abc.app.engine.security.util.SecurityUtil;

import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 通知
 * 
 * @author 陈勇
 * @date 2018年8月8日
 *
 */
@RestController
@RequestMapping(NoticeUrl.URL_NOTICE)
public class NoticeController {

	@Autowired
	private NoticeService noticeService;

	@Autowired
	private UserService userService;

	/**
	 * 通知总数
	 * 
	 * @return
	 */
	@GetMapping(value = NoticeUrl.URL_NOTICE_COUNT)
	public Long noticeCount() {
		Notice notice = new Notice();
		String username = SecurityUtil.getUsername();
		User user = userService.findUserByUsername(username);
		Date now = DateUtils.truncate(new Date(), Calendar.MINUTE);
		notice.setStartTime(now);
		notice.setEndTime(now);
		notice.setDepartmentId(user.getDepartmentId());
		return noticeService.findNoticeListCount(notice);
	}

	/**
	 * 通知列表
	 * 
	 * @param notice
	 * @return
	 */
	@PostMapping(value = NoticeUrl.URL_NOTICE_LIST)
	public PageData<Notice> noticeList(@RequestBody Notice notice) {
		String username = SecurityUtil.getUsername();
		User user = userService.findUserByUsername(username);
		Date now = new Date();
		notice.setStartTime(now);
		notice.setEndTime(now);
		notice.setDepartmentId(user.getDepartmentId());
		long count = noticeService.findNoticeListCount(notice);
		List<Notice> list = null;
		if (count > 0) {
			list = noticeService.findNoticeList(notice);
		}
		return new PageData<Notice>(list, count);
	}

	/**
	 * 通知信息
	 * 
	 * @param notice
	 * @return
	 */
	@PostMapping(value = NoticeUrl.URL_NOTICE_INFO)
	public Notice noticeInfo(@RequestBody Notice notice) {
		if (notice.getId() == null) {
			notice.setId(0L);
		}
		String username = SecurityUtil.getUsername();
		User user = userService.findUserByUsername(username);
		Notice result = noticeService.findNoticeById(notice.getId());
		// 匹配部门
		if (result != null && result.getDepartments() != null) {
			if (result.getDepartments().stream()
					.anyMatch(item -> item.getDepartmentId().equals(user.getDepartmentId()))) {
				return result;
			}
		}
		return null;
	}

}
