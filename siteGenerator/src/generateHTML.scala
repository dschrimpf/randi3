import java.io.{PrintWriter, File}
import scala.io.Source
import scala.xml.{Text, Elem, Node, XML}
import scala.xml.transform._


class ContentRewriteRule(element: Elem) extends RewriteRule {
  override def transform(n: Node): Seq[Node] =   {
    if ((n \ "@id").text == "content"){
      element

    } else {
      n
    }

  }

}

class ContentRuleTransformer(rwr: ContentRewriteRule) extends RuleTransformer(rwr)


def removeDir(folder: File) {
  // check if folder file is a real folder
  if (folder.isDirectory) {
    folder.listFiles().foreach(file => {
      removeDir(file)
  })
  }
  folder.delete()
}



val target = "target/"

//removeDir(new File(target))


(new File(target).mkdir())

val subFolders = List("generally", "instruction")

val index = XML.loadFile("resource/index.xml")

XML.save(target + "index.html", index)


(new File("resource/")).listFiles().toList.filter(file => file.getName != "index.xml" && file.getName.contains(".xml")).foreach( file => {

 val targetFileName = file.getName.subSequence(0, file.getName.size-3)+"html"

    val  page = XML.loadFile(file)

    val result = (new ContentRuleTransformer(new ContentRewriteRule(page))).transform(index)



    XML.save(target +targetFileName, result.head)

  })




