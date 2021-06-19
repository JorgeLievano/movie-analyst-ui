#!/usr/bin/env groovy
import groovy.json.JsonSlurper

def token = this.args[0]
def workSpaceId = this.args[1]
def service = this.args[2]

def jsonSlurper = new JsonSlurper()

def currentState = ["curl", "--header", "Authorization: Bearer ${token}", "--header", "Content-Type: application/vnd.api+json", "https://app.terraform.io/api/v2/workspaces/${workSpaceId}/current-state-version"].execute().text
currentState = jsonSlurper.parseText(currentState)

def outputsIds = currentState.data.relationships.outputs.data
def outputList = []

outputsIds.each { output ->
def outputobject = ["curl", "--header", "Authorization: Bearer ${token}", "https://app.terraform.io/api/v2/state-version-outputs/${output.id}"].execute().text
outputobject = jsonSlurper.parseText(outputobject).data.attributes
outputList.add(outputobject) 
}

def enviro = outputList.find {output -> output.name == service}
println enviro.value