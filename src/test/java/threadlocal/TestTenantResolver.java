/*
 *  Copyright 2018-2019 original authors
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package threadlocal;

import io.micronaut.multitenancy.exceptions.TenantNotFoundException;
import io.micronaut.multitenancy.tenantresolver.TenantResolver;

import java.io.Serializable;

public class TestTenantResolver implements TenantResolver {

    private final String tenant;

    public TestTenantResolver(String tenant) {
        this.tenant = tenant;
    }

    @Override
    public Serializable resolveTenantIdentifier() throws TenantNotFoundException {
        System.out.println("Resolve tenant from thread " + Thread.currentThread().getName());
        return tenant;
    }
}
