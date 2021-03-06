/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.apache.fineract.client.test;

import static com.google.common.truth.Truth.assertThat;
import static org.apache.fineract.client.util.Calls.ok;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import org.apache.fineract.client.util.FineractClient;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

/**
 * Illustrates use of Fineract SDK REST Client.
 *
 * @author Michael Vorburger.ch
 */
public class FineractClientTest {

    void checkClients(FineractClient fineract) throws IOException {
        assertThat(ok(fineract.clients.retrieveAll20(null, null, null, null, null, null, null, null, 0, 100, null, null, null))
                .getTotalFilteredRecords()).isAtLeast(3);
    }

    @Test
    void testRetrieveAllClientsFromFineractDev() throws IOException {
        FineractClient fineract = FineractClient.builder().baseURL("https://demo.fineract.dev/fineract-provider/api/v1/").tenant("default")
                .basicAuth("mifos", "password").build();
        checkClients(fineract);
    }

    @Test
    @Disabled // TODO remove Disabled once https://issues.apache.org/jira/browse/FINERACT-1209 is fixed
    void testRetrieveAllClientsFromLocalhostWithInsecureSelfSignedCert() throws IOException {
        FineractClient fineract = FineractClient.builder().baseURL("https://localhost:8443/fineract-provider/api/v1/").tenant("default")
                .basicAuth("mifos", "password").insecure(true).build();
        checkClients(fineract);
    }

    @Test
    @Disabled // TODO remove Ignore once https://issues.apache.org/jira/browse/FINERACT-1221 is fixed
    void testInvalidOperations() throws IOException {
        FineractClient.Builder builder = FineractClient.builder().baseURL("http://test/").tenant("default").basicAuth("mifos", "password");
        builder.getApiClient().getAdapterBuilder().validateEagerly(true); // see FINERACT-1221
        builder.build();
    }

    @Test
    void testFineractClientBuilder() throws IOException {
        assertThrows(IllegalStateException.class, () -> {
            FineractClient.builder().build();
        });
        assertThrows(IllegalStateException.class, () -> {
            FineractClient.builder().baseURL("https://server/").build();
        });
        assertThrows(IllegalStateException.class, () -> {
            FineractClient.builder().baseURL("https://server/").tenant("default").build();
        });
    }
}
