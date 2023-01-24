package dk.rheasoft.kafffe.examples.modifiers

import kafffe.bootstrap.BootstrapRoot
import kafffe.bootstrap.modifier.BootstrapPopoverModifier
import kafffe.bootstrap.modifier.BootstrapTooltipModifier
import kafffe.core.*
import kafffe.core.modifiers.*
import kafffe.core.modifiers.CssClassModifier.Companion.cssClassModifier
import kafffe.core.modifiers.StyleModifier.Companion.styleModifier
import kotlinx.browser.window
import org.w3c.dom.HTMLDivElement
import org.w3c.dom.events.Event

class Modifiers : KafffeComponent() {

    /**
     * A label component add as a child and modified to have a Bootstrap popper
     */
    val labelWithPopper = addChild(
        Label("Popper").apply {
            cssClassModifier("badge bg-secondary")
            val popperModifier = BootstrapPopoverModifier().apply {
                with(options) {
                    title = "Popper title"
                    content = "This is the popper text"
                    delay = 0
                    animation = true
                    container = "body"
                    trigger = "click hover focus"
                    boundary = "viewport"
                }
            }
            modifiers.add(popperModifier)
        }
    )

    /**
     * A label component add as a child and modified to have a Bootstrap tooltip
     */
    val labelWithTooltip = addChild(
        Label("Tooltip").apply {
            cssClassModifier("badge bg-info")
            val tooltipModifier = BootstrapTooltipModifier(Model.of("Some tooltip text")).apply {
                with(options) {
                    delay = 0
                    animation = true
                    container = "body"
                    trigger = "click hover focus"
                    boundary = "viewport"
                }
            }
            modifiers.add(tooltipModifier)
        }
    )
    /**
     * A label component added as a child and modified to have a ClickHandler showing and alert
     */
    val labelWithClick = addChild(
        Label("Clickable").apply {
            cssClassModifier("badge bg-success")
            val clickHandler = ClickHandler {event ->
                window.alert("Clicked")
            }
            modifiers.add(clickHandler)
        }
    )

    /**
     * A label component added as a child and modified to have a ClickHandler showing and alert
     */
    val labelWithClick2 = addChild(
        Label("Clickable2").apply {
            cssClassModifier("badge bg-success")
            // short hand for a EventHandler modifier that adds addEventHandler("click"
            this.on("click") {event: Event ->
                window.alert("Clicked 2")
            }
        }
    )
    /**
     * A label component added as a child and modified to be aware of viewport resize
     */
    val sizeModel: Model<String> = Model.ofGet {
        "${window.innerWidth} x ${window.innerHeight}"
    }
    val labelWithResize: Label = addChild(
        Label(sizeModel).apply {
            cssClassModifier("badge bg-info")
            val resizeAware = ResizeWindowAwareModifier( { rerender() })
            modifiers.add(resizeAware)
        }
    )


    /**
     * A label component added as a child and modified to be aware of viewport resize
     */
    val mediaAwareModel: Model<String> = Model.of(" media match? ")
    val labelMediaAware: Label = addChild(
        Label(mediaAwareModel).apply {
            cssClassModifier("badge bg-info")
            val resizeAware = MediaQueryAwareModifier(
                mediaQuery = "(max-width: 1024px)",
                onMatch = { mediaAwareModel.data = "match" },
                onNoMatch = { mediaAwareModel.data = "No match" }
            )
            modifiers.add(resizeAware)
        }
    )

    init {
        // Modify this Modifies to have style, using convenience companion function, that adds a kafffe.core.modifiers.StyleModifier
        styleModifier { fontSize = "1.5rem" }
    }

    override fun KafffeHtmlBase.kafffeHtml(): KafffeHtmlOut =
        div {
            h1 { text("Components modified by Modifers/Behaviors") }
            addSample("BootstrapPopoverModifier", "Bootstrap popper example: ", labelWithPopper)
            addSample("BootstrapTooltipModifier", "Bootstrap tootip example: ", labelWithTooltip)
            addSample("ClickHandler", "Clickable example: ", labelWithClick)
            addSample("on(\"click\", ..)", "Click using on() => addEventlistener: ", labelWithClick2)
            addSample("ResizeWindowAwareModifier", "Resize aware example: ", labelWithResize)
            addSample("MediaQueryAwareModifier", "Query = '(max-width: 1024px)'", labelMediaAware)
        }

    // Add html without making a KafffeComponent
    private fun KafffeHtml<HTMLDivElement>.addSample(title: String, description: String, childComponent: Label) {
        div {
            addClass("border rounded p-3 m-3")
            withStyle {
                display = "inline-block"
                width = "fit-content"
            }
            h2 { text(title) }
            text(description)
            add(childComponent.html)
        }
    }

}

@OptIn(ExperimentalJsExport::class)
@JsExport
fun start() {
    window.onload = {
        val root = BootstrapRoot()

        // Modify the root div to include id and CSS class attributes.
        root.modifiers.add(HtmlElementModifier.create {
            id = "bootstrap-root"
            className = "root"
        })

        root.addChild(Modifiers())

        // attach the root to the body of the HTML DOM
        root.attach()
    }
}