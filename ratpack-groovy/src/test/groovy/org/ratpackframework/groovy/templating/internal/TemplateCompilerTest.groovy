package org.ratpackframework.groovy.templating.internal

import io.netty.buffer.ByteBuf
import io.netty.buffer.Unpooled
import io.netty.util.CharsetUtil
import org.ratpackframework.groovy.script.ScriptEngine
import org.ratpackframework.util.internal.IoUtils
import spock.lang.Specification

class TemplateCompilerTest extends Specification {

  def compiler = new TemplateCompiler(new ScriptEngine<TemplateScript>(getClass().classLoader, true, TemplateScript), true)

  CompiledTemplate compile(String source) {
    compiler.compile(IoUtils.utf8Buffer(source), "test")
  }

  class StubNestedRenderer implements NestedRenderer {
    ByteBuf buffer

    @Override
    void render(String templateName, Map<String, ?> model) {
      buffer.writeBytes(IoUtils.utf8Bytes("render:${[templateName: templateName, model: model]}"))
    }
  }

  String exec(String script) {
    ByteBuf buffer = Unpooled.buffer(script.size())
    compile(script).execute([:], buffer, new StubNestedRenderer(buffer: buffer))
    buffer.toString(CharsetUtil.UTF_8)
  }

  def "compile"() {
    expect:
    exec("abc") == "abc"
    exec("1\${'2'}3") == "123"
    exec("1<% %>3") == "13"
  }

  def "rendering"() {
    expect:
    exec("a-<% render 'foo' %>-c") == "a-render:[templateName:foo, model:[:]]-c"
  }

}
