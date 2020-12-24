package com.lunatech.security.blocking.dao;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

public interface BlockingOperationRepository extends PagingAndSortingRepository<BlockingOperationEntity, Long> {

	public BlockingOperationEntity findByIpAndOperation(String ip, String operation);
	public List<BlockingOperationEntity> findByIp(String ip);
	public List<BlockingOperationEntity> findByReachedMaximum(Boolean reachedMaximum);
	//public Long countByIpAndOperation(String ip, String operation);
}
