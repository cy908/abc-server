package com.abc.app.engine.common.service.impl;

import java.util.Date;
import java.util.List;

import com.abc.app.engine.common.entity.Notice;
import com.abc.app.engine.common.entity.NoticeDepartment;
import com.abc.app.engine.common.entity.NoticeLog;
import com.abc.app.engine.common.mapper.NoticeLogMapper;
import com.abc.app.engine.common.mapper.NoticeMapper;
import com.abc.app.engine.common.service.NoticeService;
import com.abc.app.engine.common.util.LogType;
import com.abc.app.engine.security.util.SecurityUtil;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class NoticeServiceImpl implements NoticeService {

	@Autowired
	private NoticeMapper noticeMapper;

	@Autowired
	private NoticeLogMapper noticeLogMapper;

	@Override
	public List<Notice> findNoticeList(Notice notice) {
		notice.setSearch(StringUtils.trimToNull(notice.getSearch()));
		return noticeMapper.findNoticeList(notice);
	}

	@Override
	public long findNoticeListCount(Notice notice) {
		notice.setSearch(StringUtils.trimToNull(notice.getSearch()));
		return noticeMapper.findNoticeListCount(notice);
	}

	@Override
	public Notice findNoticeById(long id) {
		Notice notice = noticeMapper.findNoticeById(id);
		if (notice != null) {
			List<NoticeDepartment> list = noticeMapper.findNoticeDepartmentListById(id);
			notice.setDepartments(list);
		}
		return notice;
	}

	@Override
	public boolean saveNotice(Notice notice) {
		if (notice.getId() == null) {
			notice.setId(0L);
		}

		Date now = new Date();
		boolean success = false;
		if (notice.getId() <= 0) {
			notice.setCreateTime(now);
			noticeMapper.insertNotice(notice);
			List<NoticeDepartment> list = notice.getDepartments();
			if (list != null && list.size() > 0) {
				for (int i = 0; i < list.size(); i++) {
					list.get(i).setNoticeId(notice.getId());
				}
				noticeMapper.insertNoticeDepartment(list);
			}
			success = notice.getId() > 0 ? true : false;
			// 插入添加日志-START
			if (success) {
				String username = SecurityUtil.getUsername();
				NoticeLog noticeLog = new NoticeLog();
				noticeLog.setId(notice.getId());
				noticeLog.setLogType(LogType.INSERT);
				noticeLog.setLogTime(now);
				noticeLog.setLogUser(username);
				noticeLogMapper.insertNoticeLog(noticeLog);
				noticeLogMapper.insertNoticeDepartmentLog(noticeLog);
			}
			// 插入添加日志-END
		} else {
			notice.setUpdateTime(now);
			long rows = noticeMapper.updateNoticeById(notice);
			List<NoticeDepartment> list = notice.getDepartments();
			if (list != null && list.size() > 0) {
				NoticeDepartment noticeDepartment = list.get(0);
				noticeMapper.deleteNoticeDepartmentById(noticeDepartment.getNoticeId());
				if (noticeDepartment.getDepartmentId() != null) {
					if (noticeDepartment.getDepartmentId().longValue() > 0) {
						noticeMapper.insertNoticeDepartment(list);
					}
				}
			}
			success = rows > 0 ? true : false;
			// 插入更新日志-START
			if (success) {
				String username = SecurityUtil.getUsername();
				NoticeLog noticeLog = new NoticeLog();
				noticeLog.setId(notice.getId());
				noticeLog.setLogType(LogType.UPDATE);
				noticeLog.setLogTime(now);
				noticeLog.setLogUser(username);
				noticeLogMapper.insertNoticeLog(noticeLog);
				noticeLogMapper.insertNoticeDepartmentLog(noticeLog);
			}
			// 插入更新日志-END
		}
		return success;
	}

	@Override
	public boolean deleteNoticeById(long id) {
		// 插入删除日志-START
		Date now = new Date();
		String username = SecurityUtil.getUsername();
		NoticeLog noticeLog = new NoticeLog();
		noticeLog.setId(id);
		noticeLog.setLogType(LogType.DELETE);
		noticeLog.setLogTime(now);
		noticeLog.setLogUser(username);
		noticeLogMapper.insertNoticeLog(noticeLog);
		noticeLogMapper.insertNoticeDepartmentLog(noticeLog);
		// 插入删除日志-END
		long rows = noticeMapper.deleteNoticeById(id);
		noticeMapper.deleteNoticeDepartmentById(id);
		boolean success = rows > 0 ? true : false;
		return success;
	}

}
