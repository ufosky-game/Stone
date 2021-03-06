/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * Copyright (c) 2011, Red Hat Inc. or third-party contributors as
 * indicated by the @author tags or express copyright attribution
 * statements applied by the authors.  All third-party contributions are
 * distributed under license by Red Hat Inc.
 *
 * This copyrighted material is made available to anyone wishing to use, modify,
 * copy, or redistribute it subject to the terms and conditions of the GNU
 * Lesser General Public License, as published by the Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 * or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public License
 * for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this distribution; if not, write to:
 * Free Software Foundation, Inc.
 * 51 Franklin Street, Fifth Floor
 * Boston, MA  02110-1301  USA
 */
package com.stone.core.db.service.orm;

import java.util.Map;

import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.boot.registry.internal.StandardServiceRegistryImpl;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

/**
 * @author Steve Ebersole
 * @author crazyjohn do copy things
 */
public final class ServiceRegistryBuilder {
	private ServiceRegistryBuilder() {
	}

	public static StandardServiceRegistryImpl buildServiceRegistry() {
		return buildServiceRegistry(Environment.getProperties());
	}

	public static StandardServiceRegistryImpl buildServiceRegistry(@SuppressWarnings("rawtypes") Map serviceRegistryConfig) {
		return (StandardServiceRegistryImpl) new StandardServiceRegistryBuilder().applySettings(serviceRegistryConfig).build();
	}

	public static void destroy(ServiceRegistry serviceRegistry) {
		((StandardServiceRegistryImpl) serviceRegistry).destroy();
	}
}
