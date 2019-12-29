package com.abc.app.engine.common.controller;

import java.util.List;

import com.abc.app.engine.common.config.NoticeManageUrl;
import com.abc.app.engine.common.entity.Notice;
import com.abc.app.engine.common.service.NoticeService;
import com.abc.app.engine.common.util.PageData;
import com.abc.app.engine.common.util.ResultData;
import com.abc.app.engine.common.util.SaveType;
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
 * 通知管理
 * 
 * @author 陈勇
 * @date 2018年8月8日
 *
 */
@RestController
@RequestMapping(NoticeManageUrl.URL_NOTICE)
public class NoticeManageController {

	@Autowired
	private NoticeService noticeService;

	@Autowired
	private DepartmentService departmentService;

	/**
	 * 通知列表
	 * 
	 * @param notice
	 * @return
	 */
	@PostMapping(value = NoticeManageUrl.URL_NOTICE_LIST)
	@Secured(NoticeManageUrl.AUTH_NOTICE_LIST)
	public PageData<Notice> noticeList(@RequestBody Notice notice) {
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
	@PostMapping(value = NoticeManageUrl.URL_NOTICE_INFO)
	@Secured({ NoticeManageUrl.AUTH_NOTICE_INFO, NoticeManageUrl.AUTH_NOTICE_EDIT })
	public Notice noticeInfo(@RequestBody Notice notice) {
		if (notice.getId() == null) {
			notice.setId(0L);
		}
		return noticeService.findNoticeById(notice.getId());
	}

	/**
	 * 添加通知
	 * 
	 * @param notice
	 * @return
	 */
	@PostMapping(value = NoticeManageUrl.URL_NOTICE_ADD)
	@Secured(NoticeManageUrl.AUTH_NOTICE_ADD)
	public ResultData<?> noticeAdd(@RequestBody Notice notice) {
		return noticeSave(notice, SaveType.Add);
	}

	/**
	 * 修改通知
	 * 
	 * @param notice
	 * @return
	 */
	@PostMapping(value = NoticeManageUrl.URL_NOTICE_EDIT)
	@Secured(NoticeManageUrl.AUTH_NOTICE_EDIT)
	public ResultData<?> noticeEdit(@RequestBody Notice notice) {
		return noticeSave(notice, SaveType.Edit);
	}

	/**
	 * 保存通知
	 * 
	 * @param notice
	 * @param type
	 * @return
	 */
	private ResultData<?> noticeSave(Notice notice, SaveType type) {
		boolean success = true;
		String message = null;
		if (notice.getId() == null) {
			notice.setId(0L);
		}
		if (type == SaveType.Add && notice.getId().longValue() > 0) {
			success = false;
			message = "添加失败，参数错误！";
		} else if (type == SaveType.Edit && notice.getId().longValue() < 0) {
			success = false;
			message = "修改失败，参数错误！";
		}
		if (success) {
			success = noticeService.saveNotice(notice);
		}
		return new ResultData<>(success, message, null);
	}

	/**
	 * 删除通知
	 * 
	 * @param notice
	 * @return
	 */
	@PostMapping(value = NoticeManageUrl.URL_NOTICE_DELETE)
	@Secured(NoticeManageUrl.AUTH_NOTICE_DELETE)
	public ResultData<?> noticeDelete(@RequestBody Notice notice) {
		boolean success = true;
		String message = null;
		if (notice.getId() == null) {
			success = false;
			message = "删除失败，参数错误！";
		}
		if (success) {
			success = noticeService.deleteNoticeById(notice.getId());
		}
		return new ResultData<>(success, message, null);
	}

	/**
	 * 部门列表
	 * 
	 * @return
	 */
	@GetMapping(value = NoticeManageUrl.URL_NOTICE_DEPARTMENTS)
	@Secured({ NoticeManageUrl.AUTH_NOTICE_LIST, NoticeManageUrl.AUTH_NOTICE_INFO, NoticeManageUrl.AUTH_NOTICE_ADD,
			NoticeManageUrl.AUTH_NOTICE_EDIT })
	public List<Department> departmentList() {
		Department department = new Department();
		department.setEnable(true);// 查询启用的部门
		return departmentService.findDepartmentList(department);
	}

}
