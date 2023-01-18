@file:OptIn(ExperimentalJsExport::class)

package dk.rheasoft.kafffe.examples.hello

import kafffe.core.KafffeComponent
import kafffe.core.KafffeHtmlBase
import kafffe.core.KafffeHtmlOut
import kafffe.core.RootComponent
import kotlinx.browser.window
import kotlin.random.Random

class HelloKafffe : KafffeComponent() {
    private val names = listOf(
        "World",
        "Europe",
        "Denmark",
        "Germany",
        "Sweden",
        "Norway",
        "Greece",
        "Italy",
        "Spain",
        "Poland",
        "Finland",
        "EU"
    )

    private val currentName: String
        get() = names[Random.nextInt(names.size)]

    // Generate the HTML
    override fun KafffeHtmlBase.kafffeHtml(): KafffeHtmlOut =
        div {
            h1 {
                text("Greetings to all in: ")
                span {
                    withStyle {
                        color = "darkgreen"
                    }
                    text(currentName)
                }
            }

            button {
                text("Rerender")
                withElement {
                    addEventListener("click", { rerender() })
                }
                this.onClick {  }
            }
        }

}

@JsExport
fun start() {
    window.onload = {
        val root = RootComponent()
        root.addChild(HelloKafffe())
        // attach the root to the body of the HTML DOM
        root.attach()
    }
}
