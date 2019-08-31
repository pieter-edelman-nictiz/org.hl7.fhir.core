package org.hl7.fhir.utilities.graphql;

/*-
 * #%L
 * org.hl7.fhir.utilities
 * %%
 * Copyright (C) 2014 - 2019 Health Level 7
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

import org.hl7.fhir.exceptions.FHIRException;
import org.hl7.fhir.instance.model.api.IBaseResource;

public interface IGraphQLEngine {

  void execute() throws EGraphEngine, EGraphQLException, FHIRException;

  ObjectValue getOutput();

  void setAppInfo(Object appInfo);

  void setFocus(IBaseResource focus);

  void setGraphQL(Package graphQL);

  void setServices(IGraphQLStorageServices services);
}
