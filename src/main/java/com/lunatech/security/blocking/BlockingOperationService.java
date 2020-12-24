package com.lunatech.security.blocking;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.lunatech.security.blocking.dao.BlockingOperationEntity;
import com.lunatech.security.blocking.dao.BlockingOperationRepository;

@Service
public class BlockingOperationService {

	@Autowired
	private HttpServletRequest httpRequest;

	@Autowired
	private BlockingOperationRepository blockingRepo;

	public boolean isConnectionBlocked() {
		try {
			for (BlockingOperationEntity entity : blockingRepo.findByIp(httpRequest.getRemoteAddr())) {

				if (entity.getViolations() >= entity.getMaxViolations()) {
					return true;
				}
			}
		} catch (Throwable e) {
		}
		return false;
	}

	public void addViolation(Violations violation) {
		BlockingOperationEntity blockingEntity;
		try {
			// update existing record if exist
			blockingEntity = blockingRepo.findByIpAndOperation(httpRequest.getRemoteAddr(), violation.operation);
			blockingEntity.updateEditingDate(new Date());
			blockingEntity.addViolation();
		} catch (Throwable e) {
			// have no record
			blockingEntity = new BlockingOperationEntity(httpRequest.getRemoteAddr(), violation.operation,
					violation.maxViolations);
			blockingEntity.updateAuditing();
		}

		try {
			blockingRepo.save(blockingEntity);
		} catch (Throwable t) {

		}
	}

	@Value("${system.unblocking.interval}")
	private Long unblockingInterval;

	/*
	 * check if any reached maximum violations to be removed after passing
	 * unblockingInterval (4) minutes
	 */
	@Scheduled(fixedRate = 15 * 1000)
	public void removeReachedMaximumViolations() {
		removeBlockedOperations();
	}

	public void removeBlockedOperations() {
		List<BlockingOperationEntity> expired = new ArrayList<BlockingOperationEntity>();
		Long now = new Date().getTime();
		for (BlockingOperationEntity blockingEntity : blockingRepo.findAll()) {
			if ((now - blockingEntity.getEditedDate().getTime()) >= unblockingInterval) {
				expired.add(blockingEntity);
			}
		}
		try {
			blockingRepo.deleteAll(expired);
		} catch (Throwable t) {
		}
	}
}
