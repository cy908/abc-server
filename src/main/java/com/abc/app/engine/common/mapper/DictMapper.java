package com.abc.app.engine.common.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.abc.app.engine.common.entity.Dict;
import com.abc.app.engine.common.entity.DictOption;

@Mapper
public interface DictMapper {

	List<Dict> findDictList(Dict dict);

	long findDictListCount(Dict dict);

	List<DictOption> findDictOptionList(DictOption dictOption);

	long findDictOptionListCount(DictOption dictOption);

	Dict findDictById(long id);

	DictOption findDictOptionById(DictOption dictOption);

	long findDictOptionCount(long id);

	long insertDict(Dict dict);

	long insertDictOption(DictOption dictOption);

	long updateDictById(Dict dict);

	long updateDictOptionById(DictOption dictOption);

	long deleteDictById(long id);

	long deleteDictOptionById(DictOption dictOption);

}
