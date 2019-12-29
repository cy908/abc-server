package com.abc.app.engine.common.service;

import java.util.List;

import com.abc.app.engine.common.entity.Dict;
import com.abc.app.engine.common.entity.DictOption;

public interface DictService {

	List<Dict> findDictList(Dict dict);

	long findDictListCount(Dict dict);

	List<DictOption> findDictOptionList(DictOption dictOption);

	long findDictOptionListCount(DictOption dictOption);

	Dict findDictById(long id);

	DictOption findDictOptionById(DictOption dictOption);

	long findDictOptionCount(long id);

	boolean insertDict(Dict dict);

	boolean insertDictOption(DictOption dictOption);

	boolean updateDictById(Dict dict);

	boolean updateDictOptionById(DictOption dictOption);

	boolean deleteDictById(long id);

	boolean deleteDictOptionById(DictOption dictOption);

}
