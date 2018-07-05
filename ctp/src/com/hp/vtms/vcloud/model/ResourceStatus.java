package com.hp.vtms.vcloud.model;

public enum ResourceStatus {

	/**
	 * vApp or VM Started or power on
	 */
	STARTED,
	/**
	 * vApp or VM starting or powering on
	 */
	STARTING, STOPED, STOPING, REVERTED, REVERTING,
	/**
	 * vApp or VM starting, stopping or reverting
	 */
	IN_PROGRESS,
	/**
	 * vApp or VM started, stopped or reverted
	 */
	FINISHED

}
