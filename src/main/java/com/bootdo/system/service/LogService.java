package com.bootdo.system.service;

import org.springframework.stereotype.Service;

import com.bootdo.common.domain.sys.LogDO;
import com.bootdo.common.domain.sys.PageDO;
import com.bootdo.common.utils.Query;
@Service
public interface LogService {
	void save(LogDO logDO);
	PageDO<LogDO> queryList(Query query);
	int remove(Long id);
	int batchRemove(Long[] ids);
}
