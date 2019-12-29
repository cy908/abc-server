package com.abc.app.engine.common.service;

import java.util.List;

import com.abc.app.engine.common.entity.Notice;

public interface NoticeService {

	List<Notice> findNoticeList(Notice notice);

	long findNoticeListCount(Notice notice);

	Notice findNoticeById(long id);

	boolean saveNotice(Notice notice);

	boolean deleteNoticeById(long id);

}
