package scenery.tests.examples

import cleargl.GLMatrix
import cleargl.GLVector
import com.jogamp.opengl.GLAutoDrawable
import org.junit.Test
import scenery.*
import scenery.rendermodules.opengl.DeferredLightingRenderer
import scenery.repl.REPL
import kotlin.concurrent.thread

/**
 * <Description>
 *
 * @author Ulrik Günther <hello@ulrik.is>
 */
class CubeExample : SceneryDefaultApplication("CubeExample") {
    override fun init(pDrawable: GLAutoDrawable) {
        super.init(pDrawable)
        deferredRenderer = DeferredLightingRenderer(pDrawable.gl.gL4, glWindow!!.width, glWindow!!.height)
        hub.add(SceneryElement.RENDERER, deferredRenderer!!)

        var box = Box(GLVector(1.0f, 1.0f, 1.0f))

        var boxmaterial = Material()
        boxmaterial.ambient = GLVector(1.0f, 0.0f, 0.0f)
        boxmaterial.diffuse = GLVector(0.0f, 1.0f, 0.0f)
        boxmaterial.specular = GLVector(1.0f, 1.0f, 1.0f)
        box.position = GLVector(0.0f, 0.0f, 0.0f)
        box.material = boxmaterial

        scene.addChild(box)

        var lights = (0..0).map {
            PointLight()
        }

        lights.mapIndexed { i, light ->
            light.position = GLVector(2.0f * i, 2.0f * i, 2.0f * i)
            light.emissionColor = GLVector(0.0f, 0.1f, 0.8f)
            light.intensity = 0.8f;
            scene.addChild(light)
        }

        val cam: Camera = DetachedHeadCamera()
        cam.position = GLVector(0.0f, 0.0f, -5.0f)
        cam.view = GLMatrix().setCamera(cam.position, cam.position + cam.forward, cam.up)
        cam.projection = GLMatrix()
                .setPerspectiveProjectionMatrix(
                        70.0f / 180.0f * Math.PI.toFloat(),
                        pDrawable.surfaceWidth.toFloat() / pDrawable.surfaceHeight.toFloat(), 0.1f, 1000.0f)
                .invert()
        cam.active = true

        scene.addChild(cam)

        thread {
            while (true) {
                box.rotation.rotateByAngleY(0.01f)
                box.needsUpdate = true

                Thread.sleep(20)
            }
        }
        deferredRenderer?.initializeScene(scene)

        repl = REPL(scene, deferredRenderer!!)
        repl?.start()
        repl?.showConsoleWindow()
    }


    @Test override fun main() {
        super.main()
    }
}
