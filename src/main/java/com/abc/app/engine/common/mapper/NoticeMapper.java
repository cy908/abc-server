package com.abc.app.engine.common.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.abc.app.engine.common.entity.Notice;
import com.abc.app.engine.common.entity.NoticeDepartment;

@Mapper
public interface NoticeMapper {

	List<Notice> findNoticeList(Notice notice);

	long findNoticeListCount(Notice notice);

	Notice findNoticeById(long id);
	
	List<NoticeDepartment> findNoticeDepartmentListById(long id);

	long insertNotice(Notice notice);

	long insertNoticeDepartment(List<NoticeDepartment> list);

	long updateNoticeById(Notice notice);

	long deleteNoticeById(long id);

	long deleteNoticeDepartmentById(long id);

}
