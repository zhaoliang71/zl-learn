/**
 *    Copyright ${license.git.copyrightYears} the original author or authors.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package com.bj.zl.learn.mybatis.domain;

import java.util.*;

/**
 * 配置文件信息全部封装到该对象中
 * 1.mybatis-config配置文件
 * 2.mapper
 */
public class Configuration {


  //mybatis-config配置文件 start
  private MyEnvironment environment;



  //mybatis-config配置文件 end

  //mapper  start
  private Map<String,MyMapperStatement> mapperStatementMap;
  //mapper  end


  public Configuration(MyEnvironment environment, Map<String, MyMapperStatement> mapperStatementMap) {
    this.environment = environment;
    this.mapperStatementMap = mapperStatementMap;
  }

  public MyEnvironment getEnvironment() {
    return environment;
  }

  public void setEnvironment(MyEnvironment environment) {
    this.environment = environment;
  }

  public Map<String, MyMapperStatement> getMapperStatementMap() {
    return mapperStatementMap;
  }

  public void setMapperStatementMap(Map<String, MyMapperStatement> mapperStatementMap) {
    this.mapperStatementMap = mapperStatementMap;
  }
}
