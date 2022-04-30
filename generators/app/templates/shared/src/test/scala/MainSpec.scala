<% if (libs.includes("specs2")) { %>
import org.specs2._

class MainSpec extends Specification {
  def is = s2"""
  
  This is my first specification
    it is working $ok
    really working! $ok
  """
}
<% } %>
