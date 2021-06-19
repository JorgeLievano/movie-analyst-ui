#!/usr/bin/env groovy
import groovy.json.JsonSlurper

def token = this.args[0]
def workSpaceId = this.args[1]
def blueEnableId = this.args[2]
def blueRunningId = this.args[3]
def greenEnableId = this.args[4]
def greenRunningId = this.args[5]
def deployColor = this.args[6]

def jsonSlurper = new JsonSlurper()

if (deployColor == "blue") {
    def enableGreenPayload = "{\"data\": {\"id\": \"${greenEnableId}\",\"attributes\": {\"value\": \"true\"},\"type\": \"vars\"} }"
    def updateGreenEnable = ["curl", "-H", "Authorization: Bearer ${token}", "-H","Content-Type: application/vnd.api+json", "-X", "PATCH", "-d",enableGreenPayload, "https://app.terraform.io/api/v2/workspaces/${workSpaceId}/vars/${greenEnableId}"].execute().text
    
    def applyGreenEnablePayload = "{\"data\": {\"attributes\": {\"message\": \"Enable UI Green\"},\"type\":\"runs\",\"relationships\": {\"workspace\": {\"data\": {\"type\": \"workspaces\",\"id\": \"${workSpaceId}\"}}}}}"
    def applyGreenEnable = ["curl", "-H", "Authorization: Bearer ${token}", "-H","Content-Type: application/vnd.api+json", "-X", "POST", "-d",applyGreenEnablePayload, "https://app.terraform.io/api/v2/runs"].execute().text
    
    def runGreenPayload = "{\"data\": {\"id\": \"${greenRunningId}\",\"attributes\": {\"value\": \"true\"},\"type\": \"vars\"} }"
    def stopBluePayload = "{\"data\": {\"id\": \"${blueRunningId}\",\"attributes\": {\"value\": \"false\"},\"type\": \"vars\"} }"
    def disableBluePayload = "{\"data\": {\"id\": \"${blueEnableId}\",\"attributes\": {\"value\": \"false\"},\"type\": \"vars\"} }"
    def updateGreenRunning = ["curl", "-H", "Authorization: Bearer ${token}", "-H","Content-Type: application/vnd.api+json", "-X", "PATCH", "-d",runGreenPayload, "https://app.terraform.io/api/v2/workspaces/${workSpaceId}/vars/${greenRunningId}"].execute().text
    def updateBlueRunning = ["curl", "-H", "Authorization: Bearer ${token}", "-H","Content-Type: application/vnd.api+json", "-X", "PATCH", "-d",stopBluePayload, "https://app.terraform.io/api/v2/workspaces/${workSpaceId}/vars/${blueRunningId}"].execute().text
    def updateBlueEnable = ["curl", "-H", "Authorization: Bearer ${token}", "-H","Content-Type: application/vnd.api+json", "-X", "PATCH", "-d",disableBluePayload, "https://app.terraform.io/api/v2/workspaces/${workSpaceId}/vars/${blueEnableId}"].execute().text
    
    def applyPromoteGreenPayload = "{\"data\": {\"attributes\": {\"message\": \"Promote UI Green\"},\"type\":\"runs\",\"relationships\": {\"workspace\": {\"data\": {\"type\": \"workspaces\",\"id\": \"${workSpaceId}\"}}}}}"
    def applyPromoteGreen = ["curl", "-H", "Authorization: Bearer ${token}", "-H","Content-Type: application/vnd.api+json", "-X", "POST", "-d",applyPromoteGreenPayload, "https://app.terraform.io/api/v2/runs"].execute().text
} else if (deployColor == "green") {
    def enableBluePayload = "{\"data\": {\"id\": \"${blueEnableId}\",\"attributes\": {\"value\": \"true\"},\"type\": \"vars\"} }"
    def updateBlueEnable = ["curl", "-H", "Authorization: Bearer ${token}", "-H","Content-Type: application/vnd.api+json", "-X", "PATCH", "-d",enableBluePayload, "https://app.terraform.io/api/v2/workspaces/${workSpaceId}/vars/${blueEnableId}"].execute().text
    
    def applyBlueEnablePayload = "{\"data\": {\"attributes\": {\"message\": \"Enable UI Blue\"},\"type\":\"runs\",\"relationships\": {\"workspace\": {\"data\": {\"type\": \"workspaces\",\"id\": \"${workSpaceId}\"}}}}}"
    def applyBlueEnable = ["curl", "-H", "Authorization: Bearer ${token}", "-H","Content-Type: application/vnd.api+json", "-X", "POST", "-d",applyBlueEnablePayload, "https://app.terraform.io/api/v2/runs"].execute().text
    
    def runBluePayload = "{\"data\": {\"id\": \"${blueRunningId}\",\"attributes\": {\"value\": \"true\"},\"type\": \"vars\"} }"
    def stopGreenPayload = "{\"data\": {\"id\": \"${greenRunningId}\",\"attributes\": {\"value\": \"false\"},\"type\": \"vars\"} }"
    def disableGreenPayload = "{\"data\": {\"id\": \"${greenEnableId}\",\"attributes\": {\"value\": \"false\"},\"type\": \"vars\"} }"
    def updateBlueRunning = ["curl", "-H", "Authorization: Bearer ${token}", "-H","Content-Type: application/vnd.api+json", "-X", "PATCH", "-d",runBluePayload, "https://app.terraform.io/api/v2/workspaces/${workSpaceId}/vars/${blueRunningId}"].execute().text
    def updateGreenRunning = ["curl", "-H", "Authorization: Bearer ${token}", "-H","Content-Type: application/vnd.api+json", "-X", "PATCH", "-d",stopGreenPayload, "https://app.terraform.io/api/v2/workspaces/${workSpaceId}/vars/${greenRunningId}"].execute().text
    def updateGreenEnable = ["curl", "-H", "Authorization: Bearer ${token}", "-H","Content-Type: application/vnd.api+json", "-X", "PATCH", "-d",disableGreenPayload, "https://app.terraform.io/api/v2/workspaces/${workSpaceId}/vars/${greenEnableId}"].execute().text
    
    def applyPromoteBluePayload = "{\"data\": {\"attributes\": {\"message\": \"Promote UI Blue\"},\"type\":\"runs\",\"relationships\": {\"workspace\": {\"data\": {\"type\": \"workspaces\",\"id\": \"${workSpaceId}\"}}}}}"
    def applyPromoteBlue = ["curl", "-H", "Authorization: Bearer ${token}", "-H","Content-Type: application/vnd.api+json", "-X", "POST", "-d",applyPromoteBluePayload, "https://app.terraform.io/api/v2/runs"].execute().text
}