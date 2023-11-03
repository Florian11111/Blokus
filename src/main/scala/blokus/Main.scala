package blokus

import models.Controller

object Main {

  def main(args: Array[String]): Unit = {
    val controller = new Controller()
    
    while (true) {
      println(controller.renderField())

      println("Steuerung:")
      println("Pfeiltasten: Block bewegen")
      println("r: Block platzieren")
      println("q: Beenden")
      def captureKeyPress(): Char = {
          System.in.read().toChar
      }

      val input = captureKeyPress()

      input match {
        case 'q' => System.exit(0)
        case 'r' => {
          controller.setzen()
        }
        case 'w' => controller.move(3) // Bewegung nach oben
        case 's' => controller.move(1) // Bewegung nach unten
        case 'a' => controller.move(2) // Bewegung nach links
        case 'd' => controller.move(0) // Bewegung nach rechts
        case _ => // ignoriere andere Tastendrücke
      }
    }
  }
}
