package com.abc.app.engine.common.controller;

import java.util.List;

import com.abc.app.engine.common.config.DictManageUrl;
import com.abc.app.engine.common.entity.Dict;
import com.abc.app.engine.common.entity.DictOption;
import com.abc.app.engine.common.service.DictService;
import com.abc.app.engine.common.util.PageData;
import com.abc.app.engine.common.util.ResultData;
import com.abc.app.engine.config.DictConfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 字典管理
 * 
 * @author 陈勇
 * @date 2018年8月8日
 * 
 */
@RestController
@RequestMapping(DictManageUrl.URL_DICT)
public class DictManageController {

	@Autowired
	private DictService dictService;

	/**
	 * 字典列表
	 * 
	 * @param dict
	 * @return
	 */
	@PostMapping(value = DictManageUrl.URL_DICT_LIST)
	@Secured(DictManageUrl.AUTH_DICT_LIST)
	public PageData<Dict> dictList(@RequestBody Dict dict) {
		long count = dictService.findDictListCount(dict);
		List<Dict> list = null;
		if (count > 0) {
			list = dictService.findDictList(dict);
		}
		return new PageData<Dict>(list, count);
	}

	/**
	 * 字典信息
	 * 
	 * @param dict
	 * @return
	 */
	@PostMapping(value = DictManageUrl.URL_DICT_INFO)
	@Secured({ DictManageUrl.AUTH_DICT_LIST, DictManageUrl.AUTH_DICT_INFO, DictManageUrl.AUTH_DICT_ADD,
			DictManageUrl.AUTH_DICT_EDIT })
	public Dict dictInfo(@RequestBody Dict dict) {
		if (dict.getId() == null) {
			dict.setId(0L);
		}
		return dictService.findDictById(dict.getId());
	}

	/**
	 * 添加字典
	 * 
	 * @param dict
	 * @return
	 */
	@PostMapping(value = DictManageUrl.URL_DICT_ADD)
	@Secured(DictManageUrl.AUTH_DICT_ADD)
	public ResultData<?> dictAdd(@RequestBody Dict dict) {
		boolean success = true;
		String message = null;
		if (dict.getId() == null) {
			success = false;
			message = "添加失败，参数错误！";
		}
		if (success) {
			Dict temp = dictService.findDictById(dict.getId());
			if (temp != null) {
				success = false;
				message = "添加失败，该ID已存在！";
			}
		}
		if (success) {
			success = dictService.insertDict(dict);
		}
		return new ResultData<>(success, message, null);
	}

	/**
	 * 修改字典
	 * 
	 * @param dict
	 * @return
	 */
	@PostMapping(value = DictManageUrl.URL_DICT_EDIT)
	@Secured(DictManageUrl.AUTH_DICT_EDIT)
	public ResultData<?> dictEdit(@RequestBody Dict dict) {
		boolean success = true;
		String message = null;
		if (dict.getId() == null || dict.getOldId() == null) {
			success = false;
			message = "修改失败，参数错误！";
		}
		if (success) {
			Dict old = dictService.findDictById(dict.getOldId());
			if (old == null) {
				success = false;
				message = "修改失败，该字典不存在！";
			}
		}
		// 判断ID是否存在
		if (success && !dict.getId().equals(dict.getOldId())) {
			Dict temp = dictService.findDictById(dict.getId());
			if (temp != null) {
				success = false;
				message = "修改失败，该ID已存在！";
			}
		}
		if (success) {
			success = dictService.updateDictById(dict);
		}
		return new ResultData<>(success, message, null);
	}

	/**
	 * 删除字典
	 * 
	 * @param dict
	 * @return
	 */
	@PostMapping(value = DictManageUrl.URL_DICT_DELETE)
	@Secured(DictManageUrl.AUTH_DICT_DELETE)
	public ResultData<?> dictDelete(@RequestBody Dict dict) {
		boolean success = true;
		String message = null;
		if (dict.getId() == null) {
			success = false;
			message = "删除失败，参数错误！";
		}
		if (success) {
			Dict old = dictService.findDictById(dict.getId());
			if (old == null) {
				success = false;
				message = "删除失败，该字典不存在！";
			}
		}
		if (success) {
			long childrens = dictService.findDictOptionCount(dict.getId());
			if (childrens > 0) {
				success = false;
				message = "删除失败，该字典存在字典项！";
			}
		}
		if (success) {
			success = dictService.deleteDictById(dict.getId());
		}
		return new ResultData<>(success, message, null);
	}

	/**
	 * 字典类型列表
	 * 
	 * @return
	 */
	@GetMapping(value = DictManageUrl.URL_DICT_TYPES)
	@Secured(DictManageUrl.AUTH_DICT_LIST)
	public List<DictOption> dictTypes() {
		DictOption dictOption = new DictOption();
		dictOption.setDictId(DictConfig.DICT_TYPE);
		dictOption.setEnable(true);
		return dictService.findDictOptionList(dictOption);
	}

	/**
	 * 字典类型
	 * 
	 * @param dictOption
	 * @return
	 */
	@PostMapping(value = DictManageUrl.URL_DICT_TYPE)
	@Secured({ DictManageUrl.AUTH_DICT_INFO, DictManageUrl.AUTH_DICT_ADD, DictManageUrl.AUTH_DICT_EDIT })
	public DictOption dictType(@RequestBody DictOption dictOption) {
		dictOption.setDictId(DictConfig.DICT_TYPE);
		dictOption.setEnable(true);
		return dictService.findDictOptionById(dictOption);
	}

	/**
	 * 字典项列表
	 * 
	 * @param dictOption
	 * @return
	 */
	@PostMapping(value = DictManageUrl.URL_DICT_OPTION_LIST)
	@Secured(DictManageUrl.AUTH_DICT_LIST)
	public PageData<DictOption> dictOptionList(@RequestBody DictOption dictOption) {
		long count = dictService.findDictOptionListCount(dictOption);
		List<DictOption> list = null;
		if (count > 0) {
			list = dictService.findDictOptionList(dictOption);
		}
		return new PageData<DictOption>(list, count);
	}

	/**
	 * 字典项信息
	 * 
	 * @param dictOption
	 * @return
	 */
	@PostMapping(value = DictManageUrl.URL_DICT_OPTION_INFO)
	@Secured({ DictManageUrl.AUTH_DICT_INFO, DictManageUrl.AUTH_DICT_EDIT })
	public DictOption dictOptionInfo(@RequestBody DictOption dictOption) {
		return dictService.findDictOptionById(dictOption);
	}

	/**
	 * 添加字典项
	 * 
	 * @param dictOption
	 * @return
	 */
	@PostMapping(value = DictManageUrl.URL_DICT_OPTION_ADD)
	@Secured(DictManageUrl.AUTH_DICT_ADD)
	public ResultData<?> dictOptionAdd(@RequestBody DictOption dictOption) {
		boolean success = true;
		String message = null;
		if (dictOption.getDictId() == null || dictOption.getId() == null) {
			success = false;
			message = "添加失败，参数错误！";
		}
		if (success) {
			DictOption temp = dictService.findDictOptionById(dictOption);
			if (temp != null) {
				success = false;
				message = "添加失败，该ID已存在！";
			}
		}
		if (success) {
			success = dictService.insertDictOption(dictOption);
		}
		return new ResultData<>(success, message, null);
	}

	/**
	 * 修改字典项
	 * 
	 * @param dictOption
	 * @return
	 */
	@PostMapping(value = DictManageUrl.URL_DICT_OPTION_EDIT)
	@Secured(DictManageUrl.AUTH_DICT_EDIT)
	public ResultData<?> dictOptionEdit(@RequestBody DictOption dictOption) {
		boolean success = true;
		String message = null;
		if (dictOption.getDictId() == null || dictOption.getId() == null || dictOption.getOldId() == null) {
			success = false;
			message = "修改失败，参数错误！";
		}
		if (success) {
			DictOption param = new DictOption();
			param.setDictId(dictOption.getDictId());
			param.setId(dictOption.getOldId());
			DictOption old = dictService.findDictOptionById(param);
			if (old == null) {
				success = false;
				message = "修改失败，该字典项不存在！";
			}
		}
		// 判断ID是否存在
		if (success && !dictOption.getId().equals(dictOption.getOldId())) {
			DictOption temp = dictService.findDictOptionById(dictOption);
			if (temp != null) {
				success = false;
				message = "修改失败，该ID已存在！";
			}
		}
		if (success) {
			success = dictService.updateDictOptionById(dictOption);
		}
		return new ResultData<>(success, message, null);
	}

	/**
	 * 删除字典项
	 * 
	 * @param dictOption
	 * @return
	 */
	@PostMapping(value = DictManageUrl.URL_DICT_OPTION_DELETE)
	@Secured(DictManageUrl.AUTH_DICT_DELETE)
	public ResultData<?> dictOptionDelete(@RequestBody DictOption dictOption) {
		boolean success = true;
		String message = null;
		if (dictOption.getDictId() == null || dictOption.getId() == null) {
			success = false;
			message = "删除失败，参数错误！";
		}
		if (success) {
			DictOption old = dictService.findDictOptionById(dictOption);
			if (old == null) {
				success = false;
				message = "删除失败，该字典项不存在！";
			}
		}
		if (success) {
			success = dictService.deleteDictOptionById(dictOption);
		}
		return new ResultData<>(success, message, null);
	}

}
