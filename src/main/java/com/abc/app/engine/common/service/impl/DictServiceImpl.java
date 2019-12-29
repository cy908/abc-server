package com.abc.app.engine.common.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abc.app.engine.common.entity.Dict;
import com.abc.app.engine.common.entity.DictOption;
import com.abc.app.engine.common.mapper.DictMapper;
import com.abc.app.engine.common.service.DictService;

@Service
@Transactional(rollbackFor = Exception.class)
public class DictServiceImpl implements DictService {

	@Autowired
	private DictMapper dictMapper;

	@Override
	public List<Dict> findDictList(Dict dict) {
		dict.setSearch(StringUtils.trimToNull(dict.getSearch()));
		return dictMapper.findDictList(dict);
	}

	@Override
	public long findDictListCount(Dict dict) {
		dict.setSearch(StringUtils.trimToNull(dict.getSearch()));
		return dictMapper.findDictListCount(dict);
	}

	@Override
	public List<DictOption> findDictOptionList(DictOption dictOption) {
		dictOption.setSearch(StringUtils.trimToNull(dictOption.getSearch()));
		return dictMapper.findDictOptionList(dictOption);
	}

	@Override
	public long findDictOptionListCount(DictOption dictOption) {
		dictOption.setSearch(StringUtils.trimToNull(dictOption.getSearch()));
		return dictMapper.findDictOptionListCount(dictOption);
	}

	@Override
	public Dict findDictById(long id) {
		return dictMapper.findDictById(id);
	}

	@Override
	public DictOption findDictOptionById(DictOption dictOption) {
		return dictMapper.findDictOptionById(dictOption);
	}

	@Override
	public long findDictOptionCount(long id) {
		return dictMapper.findDictOptionCount(id);
	}

	@Override
	public boolean insertDict(Dict dict) {
		if (dict.getOrder() == null) {
			dict.setOrder(0L);
		}
		long rows = dictMapper.insertDict(dict);
		return rows > 0 ? true : false;
	}

	@Override
	public boolean insertDictOption(DictOption dictOption) {
		if (dictOption.getOrder() == null) {
			dictOption.setOrder(0L);
		}
		long rows = dictMapper.insertDictOption(dictOption);
		return rows > 0 ? true : false;
	}

	@Override
	public boolean updateDictById(Dict dict) {
		if (dict.getOrder() == null) {
			dict.setOrder(0L);
		}
		long rows = dictMapper.updateDictById(dict);
		return rows > 0 ? true : false;
	}

	@Override
	public boolean updateDictOptionById(DictOption dictOption) {
		if (dictOption.getOrder() == null) {
			dictOption.setOrder(0L);
		}
		long rows = dictMapper.updateDictOptionById(dictOption);
		return rows > 0 ? true : false;
	}

	@Override
	public boolean deleteDictById(long id) {
		long rows = dictMapper.deleteDictById(id);
		return rows > 0 ? true : false;
	}

	@Override
	public boolean deleteDictOptionById(DictOption dictOption) {
		long rows = dictMapper.deleteDictOptionById(dictOption);
		return rows > 0 ? true : false;
	}

}
