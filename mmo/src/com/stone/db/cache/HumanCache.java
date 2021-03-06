package com.stone.db.cache;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.stone.core.data.cache.holder.HumanEntityHolderCreater;
import com.stone.core.data.cache.holder.IEntityHolder;
import com.stone.core.entity.IEntity;

/**
 * The human cache;
 * 
 * @author crazyjohn
 *
 */
public class HumanCache implements ICacheObject, IEntity {
	/** the sub entity holder class */
	private Map<Class<? extends IEntity>, IEntityHolder<? extends IEntity>> subEntityHolders = new HashMap<Class<? extends IEntity>, IEntityHolder<? extends IEntity>>();
	protected long guid;
	private long playerId;
	private String name;
	private int level;

	public HumanCache() {
		this.subEntityHolders = HumanEntityHolderCreater.getHumanEntityHolder();
	}

	/**
	 * Add sub entity;
	 * 
	 * @param entity
	 * @return
	 */
	public <E extends IEntity> boolean add(E entity) {
		// get the holder
		@SuppressWarnings("unchecked")
		IEntityHolder<E> holder = (IEntityHolder<E>) this.subEntityHolders.get(entity.getClass());
		if (holder == null) {
			return false;
		}
		// add entity
		return holder.add(entity);
	}

	/**
	 * Update sub entity;
	 * 
	 * @param entity
	 * @return
	 */
	public <E extends IEntity> boolean update(E entity) {
		// get the holder
		@SuppressWarnings("unchecked")
		IEntityHolder<E> holder = (IEntityHolder<E>) this.subEntityHolders.get(entity.getClass());
		if (holder == null) {
			return false;
		}
		// update entity
		return holder.update(entity);
	}

	/**
	 * Remove sub entity;
	 * 
	 * @param entityClass
	 * @param id
	 */
	public <E extends IEntity> void remove(Class<?> entityClass, Serializable id) {
		// get the holder
		@SuppressWarnings("unchecked")
		IEntityHolder<E> holder = (IEntityHolder<E>) this.subEntityHolders.get(entityClass);
		if (holder == null) {
			return;
		}
		// remove entity
		holder.remove(id);
	}

	/**
	 * Is cache modified;
	 * 
	 * @return
	 */
	public boolean isModified() {
		for (IEntityHolder<? extends IEntity> holder : this.subEntityHolders.values()) {
			if (holder.isModified()) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Reset modified;
	 */
	public void resetModified() {
		for (IEntityHolder<? extends IEntity> holder : this.subEntityHolders.values()) {
			holder.resetModified();
		}
	}

	public void removeAll() {
		for (IEntityHolder<? extends IEntity> holder : this.subEntityHolders.values()) {
			holder.removeAll();
		}
	}

	public void setHumanGuid(long guid) {
		this.guid = guid;
	}

	public long getHumanGuid() {
		return guid;
	}

	/**
	 * Get sub entities;
	 * 
	 * @param entityClass
	 * @return
	 */
	public <E extends IEntity> Collection<E> getEntites(Class<E> entityClass) {
		// get the holder
		@SuppressWarnings("unchecked")
		IEntityHolder<E> holder = (IEntityHolder<E>) this.subEntityHolders.get(entityClass);
		if (holder == null) {
			return new ArrayList<E>();
		}
		return holder.getEntities();
	}

	public Map<Class<? extends IEntity>, IEntityHolder<? extends IEntity>> getSubEntityHolders() {
		return subEntityHolders;
	}

	public void setSubEntityHolders(Map<Class<? extends IEntity>, IEntityHolder<? extends IEntity>> subEntityHolders) {
		this.subEntityHolders = subEntityHolders;
	}

	public long getPlayerId() {
		return playerId;
	}

	public void setPlayerId(long playerId) {
		this.playerId = playerId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	@Override
	public long getId() {
		return this.getHumanGuid();
	}

}
