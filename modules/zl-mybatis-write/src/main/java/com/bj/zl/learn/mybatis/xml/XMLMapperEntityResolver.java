/**
 * Copyright ${license.git.copyrightYears} the original author or authors.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.bj.zl.learn.mybatis.xml;

import com.bj.zl.learn.mybatis.io.MyResource;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;

/**
 * 对MyBatis DTD 约束文件的离线实体解析(不需要联网,从本地寻找DTD文件)
 * Offline entity resolver for the MyBatis DTDs.
 */
public class XMLMapperEntityResolver implements EntityResolver {

    private static final String MYBATIS_CONFIG_SYSTEM = "mybatis-1.0-config.dtd";
    private static final String MYBATIS_MAPPER_SYSTEM = "mybatis-1.0-mapper.dtd";
                                                 //     com\bj\zl\learn\mybatis\parsing\mybatis-1.0-config.dtd
    private static final String MYBATIS_CONFIG_DTD = "com/bj/zl/learn/mybatis/parsing/mybatis-1.0-config.dtd";
    private static final String MYBATIS_MAPPER_DTD = "com/bj/zl/learn/mybatis/parsing/mybatis-1.0-mapper.dtd";

    private InputSource getInputSource(String path, String publicId, String systemId) {
        InputSource source = null;
        if (path != null) {
            try {
                InputStream in = MyResource.getResourceAsStream(path);
                source = new InputSource(in);
                source.setPublicId(publicId);
                source.setSystemId(systemId);
            } catch (Exception e) {
                // ignore, null is ok
            }
        }
        return source;
    }
    /**
     * Converts a public DTD into a local one.
     *
     * @param publicId The public id that is what comes after "PUBLIC"
     * @param systemId The system id that is what comes after the public id.
     * @return The InputSource for the DTD
     * @throws SAXException If anything goes wrong
     */

    public InputSource resolveEntity(String publicId, String systemId) throws SAXException, IOException {
        try {
            if (systemId != null) {
                String lowerCaseSystemId = systemId.toLowerCase(Locale.ENGLISH);
                //mybatis-1.0-config.dtd
                if (lowerCaseSystemId.contains(MYBATIS_CONFIG_SYSTEM)) {
                    return getInputSource(MYBATIS_CONFIG_DTD, publicId, systemId);
                } else if (lowerCaseSystemId.contains(MYBATIS_MAPPER_SYSTEM)) {
                    //mybatis-1.0-mapper.dtd
                    return getInputSource(MYBATIS_MAPPER_DTD, publicId, systemId);
                }
            }
            return null;
        } catch (Exception e) {
            throw new SAXException(e.toString());
        }
    }
}
