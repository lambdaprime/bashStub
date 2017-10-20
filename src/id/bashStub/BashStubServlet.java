/* 
 *
 * This source file is a part of bashStub project.
 * Description for this  project can be found in README.org
 *
 * bashStub is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * bashStub is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with bashStub. If not, see <http://www.gnu.org/licenses/>.
 *
 */

package id.bashStub;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class BashStubServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public BashStubServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException 
    {
        handle(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        handle(request, response);
    }

    void handle(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String script = request.getMethod().toLowerCase() + ".sh";
        
        Process proc = Runtime.getRuntime().exec(new String[] {"/bin/bash", 
                getServletContext().getRealPath(script), headers(request), 
                params(request.getParameterMap())});
        pipe(new InputStreamReader(request.getInputStream()), 
                new OutputStreamWriter(proc.getOutputStream()));
        proc.getOutputStream().close();
        pipe(new InputStreamReader(proc.getInputStream()), 
                response.getWriter());
    }

    private static String headers(HttpServletRequest request) {
        StringBuilder buf = new StringBuilder();
        Enumeration<?> names = request.getHeaderNames();
        while (names.hasMoreElements()) {
            String name = (String) names.nextElement();
            buf.append(name + "=" + request.getHeader(name)).append('\n');
        }
        return buf.toString();
    }
    
    private static String params(Map<String, ?> parameterMap) {
        StringBuilder buf = new StringBuilder();
        for (Object k: parameterMap.keySet()) {
            String name = (String) k;
            String val = "";
            if (parameterMap.get(name) != null)
                val = Arrays.asList((String[])parameterMap.get(name)).toString();
            buf.append(name).append("=").append(val).append('\n');
        }
        return buf.toString();
    }
    
    private static void pipe(Reader reader, Writer writer) {
        char[] buf = new char[1024];
        try {
            int len = 0;
            while ((len = reader.read(buf)) != -1) {
                writer.write(buf, 0, len);
            }
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
