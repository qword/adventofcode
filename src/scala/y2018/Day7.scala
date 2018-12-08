package y2018

import scala.collection.mutable.ListBuffer
import scala.io.Source

case class Step(id: String, dependsOn: ListBuffer[String])

object Day7 {
  def main(args: Array[String]): Unit = {

    val steps = ListBuffer.empty[Step]
    var stepsDone = ListBuffer.empty[String]
    var stepsDoing = scala.collection.mutable.HashMap.empty[String, Int]
    var stepsTodo = scala.collection.mutable.HashMap.empty[String, Int]

    for (x <- 0 until 26) {
      val c = (x + 65).toChar.toString
      stepsTodo += (c -> (x + 61))
    }

    def addStep(id: String, dependsOn: String = null): Unit = {
      val existingStep = steps.find(_.id == id)
      if (existingStep.isDefined) {
        if (dependsOn != null)
          existingStep.get.dependsOn += dependsOn
      } else {
        val depencencies = ListBuffer.empty[String]
        if (dependsOn != null)
          depencencies += dependsOn
        steps += Step(id, depencencies)
      }
    }
    // create dependencies
    Source.fromFile("input/2018/day7").getLines.foreach(line => {
      val tokens = line.split(" ")
      val dependsOn = tokens(1)
      val id = tokens(7)

      addStep(id, dependsOn)
      addStep(dependsOn)
    })

    var timeSpent = 0
    var sortedSteps = stepsTodo.keySet.toArray.sorted

    def nextCandidate(): Step = {
      var candidate: Step = null
      var i = 0
      while(i < sortedSteps.length) {
        val currentStep = steps.find(_.id == sortedSteps(i))

        if (currentStep.get.dependsOn.isEmpty || containsAll(stepsDone, currentStep.get.dependsOn)) {
          return currentStep.get
        }

        i += 1
      }
      candidate
    }

    def move(step: Step): Unit = {
      if (stepsDoing.size < 5) {
        stepsDoing += (step.id -> stepsTodo(step.id))
        sortedSteps = sortedSteps.filterNot(_ == step.id)
      }
    }

    def moveCandidates() = {
      var candidate = nextCandidate()
      while (candidate != null && stepsDoing.size < 5) {
        move(candidate)
        candidate = nextCandidate()
      }
    }

    while (stepsDone.size < 26) {
      moveCandidates()

      for ((k, v) <- stepsDoing) {
        val remaining = v - 1
        if (remaining > 0) {
          stepsDoing += (k -> remaining)
        } else {
          stepsDone += k
          stepsDoing -= k
        }
      }

      timeSpent += 1
    }

    println(s"Time spent: $timeSpent")
    print(s"Result: ")
    stepsDone.foreach(print)
  }

  def containsAll(container: ListBuffer[String], list: ListBuffer[String]): Boolean = {
    var containsAll = true
    for (l <- list) if (!container.contains(l)) containsAll = false
    containsAll
  }
}

