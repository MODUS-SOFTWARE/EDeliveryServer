///*
// * Copyright (C) 2017 modussa
// *
// * This program is free software; you can redistribute it and/or
// * modify it under the terms of the GNU General Public License
// * as published by the Free Software Foundation; either version 2
// * of the License, or (at your option) any later version.
// *
// * This program is distributed in the hope that it will be useful,
// * but WITHOUT ANY WARRANTY; without even the implied warranty of
// * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// * GNU General Public License for more details.
// *
// * You should have received a copy of the GNU General Public License
// * along with this program; if not, write to the Free Software
// * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
// */
//package com.edelivery.edeliveryserver.documentserver;
//
//import JavaPapyrusR6ServerApi.Constants;
//import JavaPapyrusR6ServerApi.DataTypes.DocumentApi;
//import JavaPapyrusR6ServerApi.DataTypes.ProcNextStepApi;
//import JavaPapyrusR6ServerApi.DataTypes.RoutingApi;
//import JavaPapyrusR6ServerApi.DataTypes.WMTErrRetType;
//import JavaPapyrusR6ServerApi.DocumentServices;
//import JavaPapyrusR6ServerApi.Errors;
//import JavaPapyrusR6ServerApi.SocketPapyrus;
//import com.edelivery.edeliveryserver.constants.DocumentConstants;
//import com.edelivery.edeliveryserver.constants.FolderConstants;
//import com.edelivery.edeliveryserver.constants.RoutingConstants;
////import com.edelivery.edeliveryserver.db.models.Document;
//import com.edelivery.edeliveryserver.exception.DocumentServerException;
////import com.edelivery.edeliveryserver.handlers.DocumentHandler;
////import gr.modus.papyrus.restservice.constants.DocumentConstants;
////import gr.modus.papyrus.restservice.constants.RoutingConstants;
////import gr.modus.papyrus.restservice.constants.FolderConstants;
////import gr.modus.papyrus.restservice.db.PDocumentHandler;
////import gr.modus.papyrus.restservice.exception.DocumentServerException;
////import gr.modus.papyrus.restservice.model.PDocument;
//import java.io.BufferedOutputStream;
//import java.io.InputStream;
//import java.io.OutputStream;
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.Date;
//import java.util.List;
//import java.util.ResourceBundle;
//import java.util.Set;
//import java.util.logging.Level;
//import java.util.logging.Logger;
//import java.util.stream.Collectors;
//import java.util.stream.IntStream;
//import javax.annotation.PostConstruct;
//import javax.annotation.PreDestroy;
//import javax.ejb.EJB;
//import javax.ejb.Stateless;
//import javax.ws.rs.core.MediaType;
//
///**
// * This class acts as a facade for the legacy Papyrus API
// * {@link JavaPapyrusR6ServerApi.DocumentServices}, providing a clean way of
// * interacting with the Papyrus Document Server. This class is a
// * {@link Stateless} EJB and it's lifecycle will be managed by the Application
// * Server. Instances of this class will be created according to request load.
// *
// * @author Charalampos Chomenidis
// */
//@Stateless
//public class DocumentServer {
//
//    @EJB
//    DocumentHandler documentHandler;
//
//    private static final Logger LOG = Logger.getLogger(DocumentServer.class.getName());
//
//    private static final int DOCUMENT_EMPTY_OR_CASE = -2094923000;
//    private static final int PDF_CONVERT_FAILED = -2094923407;
//    private static final int DOCUMENT_TYPE_INVALID_FOR_PDF = -2094922999;
//
//    public static String DELIMETER = ";";
//
//    private DocumentServices services;
//    private SocketPapyrus socket;
//
//    private ResourceBundle config;
//
//    @PostConstruct
//    private void init() {
//        config = ResourceBundle.getBundle("config");
//        services = new DocumentServices(config.getString("DocumentServerHost"), Integer.parseInt(config.getString("DocumentServerPort")));
//    }
//
//    @PreDestroy
//    private void destroy() {
//        services.finalSockets(socket);
//    }
//
//    /**
//     * Initializes a network connection to the Document Server. This connection
//     * will be automatically closed by the Document Server if the channel stays
//     * idle for some time.
//     */
//    public void start() {
//        LOG.log(Level.INFO, "Initializing Document Server Sockets.");
//        socket = services.initSockets();
//        services.setSocket(socket);
//    }
//
//    /**
//     * Closes a previously initialized network connection to the Document
//     * Server.
//     */
//    public void stop() {
//        LOG.log(Level.INFO, "Closing up Document Server Sockets.");
//        services.finalSockets(socket);
//    }
//
//    /**
//     * Populates the provided BufferedOutputStream with the data of a specified
//     * Document.
//     *
//     * @param userId the Id of the User that makes the view action.
//     * @param documentId the Id of the Document whose data need to be fetched by
//     * the Document Server.
//     * @param buffer the BufferedOutputStream that will be populated with the
//     * specified Document's data.
//     * @return the type of the document
//     * @throws gr.modus.papyrus.restservice.exception.DocumentServerException
//     */
//    public void getDocument(Integer userId, Integer documentId, BufferedOutputStream buffer, Boolean isPDF) throws DocumentServerException {
//        try {
//            //TODO: Use type 20
//            Integer docType = 1;
//            if (isPDF) {
//                docType = 20;
//            }
//            WMTErrRetType response = services.GetDocumentR6(userId, documentId, 0, docType, 512000, buffer);
//            System.out.println(response.getData());
//            if (response.hasNoErrorsGetDocR6()) {
//                return;
//            } else {
//                LOG.log(Level.SEVERE, response.getErrorMessage());
//                LOG.log(Level.SEVERE, response.getExceptionMessage());
//                throw new DocumentServerException(response.getErrorMessage());
//            }
//        } catch (Exception ex) {
//            LOG.log(Level.SEVERE, ex.getMessage());
//            throw new DocumentServerException("Error while attempting to fetch data from Document Server for document:" + documentId, ex);
//        }
//    }
//
//    public String getDocument(Integer userId, Integer documentId, OutputStream stream, Boolean isPDF) throws DocumentServerException {
//        try {
//            Integer docType = 1;
//            if (isPDF) {
//                docType = 20;
//            }
//            WMTErrRetType response = services.GetDocumentR6(userId, documentId, 0, docType, 512000, stream);
//            System.out.println(response.getData());
//            if (!response.hasNoErrors()) {
//                LOG.log(Level.SEVERE, response.getErrorMessage());
//                LOG.log(Level.SEVERE, response.getExceptionMessage());
//                throw new DocumentServerException(response.getErrorMessage());
//            }
//            return response.getData().get(1).toString();
//        } catch (Exception ex) {
//            LOG.log(Level.SEVERE, ex.getMessage());
//            throw new DocumentServerException("Error while attempting to fetch data from Document Server for document:" + documentId, ex);
//        }
//    }
//
//    /**
//     * Finds the default media type of a specified Document. For example, if the
//     * Document is saved as a pdf file, this function will return:
//     * "application/pdf"
//     *
//     * @param userId the Id of the User that makes the view action.
//     * @param documentId the Id of the Document whose media type needs to be
//     * found.
//     * @return the Media Type of the specified Document.
//     */
//    public String getDocumentMediaType(Integer userId, Integer documentId) {
//        List responseData = services.GetImageInfo(userId, documentId).getData();
//        String fileType = (String) responseData.get(5).toString();
//
//        return getMediaType(fileType);
//    }
//
//    /**
//     * Gets the next unique Id for a Dispatch Entity from the Document Server.
//     * The server will never return the same Id even if the Id was never used.
//     *
//     * @return @throws DocumentServer
//     */
//    public Integer getNextDispatchId() throws DocumentServerException {
//        try {
//            WMTErrRetType result = services.GetNextId(RoutingConstants.ROUTING_OBECT_TYPE);
//            return (Integer) result.getData().get(0);
//        } catch (Exception ex) {
//            Logger.getLogger(DocumentServer.class.getName()).log(Level.SEVERE, "Could not get Next Dispatch Id from Document server", ex);
//            throw new DocumentServerException("Could not get Next Dispatch Id from Document server", ex);
//        }
//    }
//
//    public Integer getNextFolderId() throws DocumentServerException {
//        try {
//            WMTErrRetType result = services.GetNextId(FolderConstants.P_OBJECT_TYPE_FOLDER);
//            return (Integer) result.getData().get(0);
//        } catch (Exception ex) {
//            Logger.getLogger(DocumentServer.class.getName()).log(Level.SEVERE, "Could not get Next Folder Id from Document server", ex);
//            throw new DocumentServerException("Could not get Next Folder Id from Document server", ex);
//        }
//    }
//
//    public Integer getNextTaskId() throws DocumentServerException {
//        try {
//            WMTErrRetType result = services.GetNextId(51);
//            return (Integer) result.getData().get(0);
//        } catch (Exception ex) {
//            Logger.getLogger(DocumentServer.class.getName()).log(Level.SEVERE, "Could not get Next Task Id from Document server", ex);
//            throw new DocumentServerException("Could not get Next Task Id from Document server", ex);
//        }
//    }
//
//    public Integer getNextReminderId() throws DocumentServerException {
//        try {
//            WMTErrRetType result = services.GetNextId(52);
//            return (Integer) result.getData().get(0);
//        } catch (Exception ex) {
//            Logger.getLogger(DocumentServer.class.getName()).log(Level.SEVERE, "Could not get Next Reminder Id from Document server", ex);
//            throw new DocumentServerException("Could not get Next Reminder Id from Document server", ex);
//        }
//    }
//
//    public DocumentApi insertDocument(Integer userId, String filename, String description, InputStream stream) throws DocumentServerException {
//        WMTErrRetType response = services.InsertDocument(userId, filename, description, stream, 0);
//        if (!response.hasNoErrors()) {
//            LOG.log(Level.SEVERE, "Error while trying to upload a file to the Document Server: {0}", response.getErrorMessage());
//            throw new DocumentServerException(response.getErrorMessage());
//        }
//        return documentHandler.findComplete(services.getDocId());
//    }
//
//    public DocumentApi insertEmptyDocument(Integer userId, String description) throws DocumentServerException {
//        WMTErrRetType response = services.InsertEmptyDocument(userId, description, 0);
//        if (!response.hasNoErrors()) {
//            LOG.log(Level.SEVERE, "Error while trying to upload a file to the Document Server: {0}", response.getErrorMessage());
//            throw new DocumentServerException(response.getErrorMessage());
//        }
//        return documentHandler.findComplete(services.getDocId());
//    }
//
//    public void setDocumentDescription(Integer userId, Integer documentId, String description) throws DocumentServerException {
//        WMTErrRetType response = services.SetImageDescription(userId, documentId, description);
//        if (!response.hasNoErrors()) {
//            LOG.log(Level.SEVERE, "Error while trying to update image description to the Document Server: {0}", response.getErrorMessage());
//            throw new DocumentServerException(response.getErrorMessage());
//        }
//    }
//
//    public void setDocumentDescriptionMul(Integer userId, Integer documentId, String description, Integer count) throws DocumentServerException {
//        WMTErrRetType response = services.SetImageDescriptionMul(userId, documentId, count, description);
//        if (!response.hasNoErrors()) {
//            LOG.log(Level.SEVERE, "Error while trying to update image description to the Document Server: {0}", response.getErrorMessage());
//            throw new DocumentServerException(response.getErrorMessage());
//        }
//    }
//
//    public Boolean isPDF(Integer documentId) {
//        WMTErrRetType response = services.dsCanConvertFile2Pdf(documentId);
//        if (!response.hasNoErrors()) {
//            return Boolean.FALSE;
//        }
//        return (Boolean) response.getData().get(0);
//    }
//
//    public DocumentApi insertTask(Integer userId, String description) throws DocumentServerException {
//        WMTErrRetType response = services.InsertEmptyDoc(userId, "", description, DocumentConstants.Type.TASK.getId(), 0, 0, 1);
//        if (!response.hasNoErrors()) {
//            LOG.log(Level.SEVERE, "Error while trying to insert a task to the Document Server: {0}", response.getErrorMessage());
//            throw new DocumentServerException(response.getErrorMessage());
//        }
//        return documentHandler.findComplete(response.getData().get(0));
//    }
//
//    public Integer insertCalendarEvent(Integer userId, String description) throws DocumentServerException {
//        WMTErrRetType response = services.InsertEmptyDoc(userId, "", description, DocumentConstants.Type.EVENT.getId(), 0, 0, 1);
//        if (!response.hasNoErrors()) {
//            LOG.log(Level.SEVERE, "Error while trying to insert a calendar event to the Document Server: {0}", response.getErrorMessage());
//            throw new DocumentServerException(response.getErrorMessage());
//        }
//        return (Integer) response.getData().get(0);
//    }
//
//    public List<Integer> insertCalendarEvents(Integer userId, String description, Integer count) throws DocumentServerException {
//        WMTErrRetType response = services.InsertEmptyDoc(userId, "", description, DocumentConstants.Type.EVENT.getId(), 0, 0, count);
//        if (!response.hasNoErrors()) {
//            LOG.log(Level.SEVERE, "Error while trying to insert a calendar event to the Document Server: {0}", response.getErrorMessage());
//            throw new DocumentServerException(response.getErrorMessage());
//        }
//        Integer firstId = (Integer) response.getData().get(0);
//        return IntStream.range(firstId, firstId + count)
//                .boxed()
//                .collect(Collectors.toList());
//    }
//
//    public List<ProcNextStepApi> GetFirstStepData(Integer userID, Integer procedureID) {
//        this.start();
//        WMTErrRetType errorLoc = services.GetFirstStepData(userID, procedureID);
//        List data = errorLoc.getData();
//        String stepIDsStr = ((StringBuffer) data.get(0)).toString();
//        String stepNamesStr = ((StringBuffer) data.get(1)).toString();
//        Integer fieldsFlag = ((Integer) data.get(2));
//        String dispatchModesStr = ((StringBuffer) data.get(3)).toString();
//        String usersGroupsToStr = ((StringBuffer) data.get(4)).toString();
//        String actionsStr = ((StringBuffer) data.get(5)).toString();
//        String commentsStr = ((StringBuffer) data.get(6)).toString();
//        Integer priority = ((Integer) data.get(7));
//
//        Integer chargeToUsersOfGroup = ((Integer) data.get(8));
//        Integer endDays = ((Integer) data.get(9));
//        Integer endHours = ((Integer) data.get(10));
//        Integer endMinutes = ((Integer) data.get(11));
//
//        List<Integer> stepIDs = Arrays.asList(stepIDsStr.trim().split(DELIMETER))
//                .stream()
//                .filter(i -> !i.isEmpty())
//                .map(i -> Integer.parseInt(i.trim()))
//                .collect(Collectors.toList());
//
//        List<ProcNextStepApi> output = new ArrayList<>();
//        if (stepIDs.size() == 1) {
//            ProcNextStepApi p = new ProcNextStepApi();
//            p.setStepId(stepIDs.get(0));
//            p.setStepName(stepNamesStr);
//            p.setFieldsFlag(fieldsFlag);
//            List<Integer> usersGroupsToList = Arrays.asList(usersGroupsToStr.trim().split(DELIMETER))
//                    .stream()
//                    .filter(i -> !i.isEmpty())
//                    .map(i -> Integer.parseInt(i.trim()))
//                    .collect(Collectors.toList());
//            p.setUsersGroupsToIds(usersGroupsToList);
//            List<Integer> actionsList = Arrays.asList(actionsStr.trim().split(DELIMETER))
//                    .stream()
//                    .filter(i -> !i.isEmpty())
//                    .map(i -> Integer.parseInt(i.trim()))
//                    .collect(Collectors.toList());
//            p.setActionsIds(actionsList);
//            p.setComments(commentsStr);
//            p.setPriority(priority);
//            p.setProcedureID(procedureID);
//            List<Integer> dispatchModes = Arrays.asList(dispatchModesStr.trim().split(DELIMETER))
//                    .stream()
//                    .filter(i -> !i.isEmpty())
//                    .map(i -> Integer.parseInt(i.trim()))
//                    .collect(Collectors.toList());
//            p.setDispatchModes(dispatchModes);
//
//            p.setChargeToUsersOfGroup(chargeToUsersOfGroup);
//            p.setEndDays(endDays);
//            p.setEndHours(endHours);
//            p.setEndMinutes(endMinutes);
//
//            output.add(p);
//        } else {
//            List<String> stepNames = Arrays.asList(stepNamesStr.trim().split(DELIMETER));
//            for (int i = 0; i < stepIDs.size(); i++) {
//                ProcNextStepApi p = new ProcNextStepApi();
//                p.setStepId(stepIDs.get(i));
//                p.setStepName(stepNames.get(i));
//                output.add(p);
//            }
//        }
//        this.stop();
//        return output;
//    }
//
//    public List<ProcNextStepApi> GetNextStepData(Integer userID, Integer chargeID, Integer dispatchModeID) {
//        this.start();
//        WMTErrRetType errorLoc = services.GetNextStepData(userID, chargeID, dispatchModeID);
//
//        List data = errorLoc.getData();
//        String stepNameStr = ((StringBuffer) data.get(0)).toString();
//        Integer fieldsFlag = ((Integer) data.get(1));
//        String usersGroupsTo = ((StringBuffer) data.get(2)).toString();
//        String actionsStr = ((StringBuffer) data.get(3)).toString();
//        String commentsStr = ((StringBuffer) data.get(4)).toString();
//        Integer priority = (Integer) data.get(5);
//        Integer procedureID = (Integer) data.get(6);
//        String nextStepIDsStr = ((StringBuffer) data.get(7)).toString();
//
//        Integer isServerStep = (Integer) data.get(8);
//        Integer chargeToUsersOfGroup = (Integer) data.get(9);
//        Integer endDays = (Integer) data.get(10);
//        Integer endHours = (Integer) data.get(11);
//        Integer endMinutes = (Integer) data.get(12);
//
//        List<Integer> stepIDs = Arrays.asList(nextStepIDsStr.trim().split(DELIMETER))
//                .stream()
//                .filter(i -> !i.isEmpty())
//                .map(i -> Integer.parseInt(i.trim()))
//                .collect(Collectors.toList());
//
//        List<ProcNextStepApi> output = new ArrayList<>();
//        if (stepIDs.size() == 1) {
//            ProcNextStepApi p = new ProcNextStepApi();
//            p.setStepId(stepIDs.get(0));
//            p.setStepName(stepNameStr);
//            p.setFieldsFlag(fieldsFlag);
//            List<Integer> usersGroupsToList = Arrays.asList(usersGroupsTo.trim().split(DELIMETER))
//                    .stream()
//                    .filter(i -> !i.isEmpty())
//                    .map(i -> Integer.parseInt(i.trim()))
//                    .collect(Collectors.toList());
//            p.setUsersGroupsToIds(usersGroupsToList);
//            List<Integer> actionsList = Arrays.asList(actionsStr.trim().split(DELIMETER))
//                    .stream()
//                    .filter(i -> !i.isEmpty())
//                    .map(i -> Integer.parseInt(i.trim()))
//                    .collect(Collectors.toList());
//            p.setActionsIds(actionsList);
//            p.setComments(commentsStr);
//            p.setPriority(priority);
//            p.setProcedureID(procedureID);
//
//            p.setServerStep(isServerStep);
//            p.setChargeToUsersOfGroup(chargeToUsersOfGroup);
//            p.setEndDays(endDays);
//            p.setEndHours(endHours);
//            p.setEndMinutes(endMinutes);
//            //List<Integer> dispatchModes = ApiUtils.str2List(dispatchModesStr);
//            //p.setDispatchModes(dispatchModes);
//            output.add(p);
//        } else {
//            List<String> stepNames = Arrays.asList(stepNameStr.trim().split(DELIMETER));
//            for (int i = 0; i < stepIDs.size(); i++) {
//                ProcNextStepApi p = new ProcNextStepApi();
//                p.setStepId(stepIDs.get(i));
//                // p.setStepName(stepNames.get(i));
//                p.setStepName("παραλληλο " + i); //TODO pending from document server- revert!
//                output.add(p);
//            }
//        }
//        this.stop();
//        return output;
//    }
//
//    public String RoutingNew(Integer docID, Integer userID, Set<Integer> userGroupsTo, Date dateEnd, String comments, Set<Integer> actions, Set<Integer> dispachModes, Integer priority, Boolean sendToUsersOfGroup) {
//
//        String userGroupsString = userGroupsTo.stream()
//                .map(u -> u.toString())
//                .collect(Collectors.joining(DELIMETER));
//
//        String actionsString = actions.stream()
//                .map(u -> u.toString())
//                .collect(Collectors.joining(DELIMETER));
//
//        String dispatchModesString = dispachModes.stream()
//                .map(u -> u.toString())
//                .collect(Collectors.joining(DELIMETER));
//
//        String dateEndString;
//        if (dateEnd == null) {
//            dateEndString = "";
//        } else {
//            dateEndString = new SimpleDateFormat(ResourceBundle.getBundle("messages").getString("TimeStampDocServerFormat")).format(dateEnd);
//        }
//        WMTErrRetType errorLoc = services.RoutingNew(docID, userID, userGroupsString, dateEndString, comments != null ? comments : "", actionsString, dispatchModesString, priority, sendToUsersOfGroup != null && sendToUsersOfGroup ? 1 : 0, "");
//        String output = errorLoc.getData().get(0).toString();
//
//        return output;
//    }
//
//    public String ProcedureNew(Integer docId, Integer procId, Integer userId, Set<Integer> userGroupsTo, String comments, Set<Integer> actions, Integer priority) {
//        String userGroupsString = userGroupsTo.stream()
//                .map(u -> u.toString())
//                .collect(Collectors.joining(DELIMETER));
//
//        String actionsString = actions.stream()
//                .map(u -> u.toString())
//                .collect(Collectors.joining(DELIMETER));
//
//        WMTErrRetType errorLoc = services.StartProcPrepare(docId, userId, procId);
//
//        List data = errorLoc.getData();
//        Integer instanceId = (Integer) data.get(0);
//
//        errorLoc = services.StartProcCommit(userId, instanceId, userGroupsString, comments != null ? comments : "", actionsString, priority);
//        String routids = errorLoc.getData().get(0).toString();
//        return routids;
//    }
//
//    public Integer RoutingTerminate(Integer userID, Integer chargeID, Integer dispatchModeID, String comments, Set<Integer> procStepUsersGroupsTo, Set<Integer> procStepActions, String procStepComments, Integer procStepPriority) {
//
//        String userGroupsString = procStepUsersGroupsTo.stream()
//                .map(u -> u.toString())
//                .collect(Collectors.joining(DELIMETER));
//
//        String actionsString = procStepActions.stream()
//                .map(u -> u.toString())
//                .collect(Collectors.joining(DELIMETER));
//
//        this.start();
//        WMTErrRetType errorLoc = services.RoutingTerminate(userID, chargeID, dispatchModeID, comments, userGroupsString, actionsString, procStepComments, procStepPriority, "");
//        this.stop();
//
//        Integer terminateStatus = (Integer) errorLoc.getData().get(0);
//        return terminateStatus;
//    }
//
//    /**
//     * Converts a file's extension to the appropriate media type. For example:
//     * pdf -> "application/pdf" txt -> "text/plain" etc.
//     *
//     * @param fileType
//     * @return
//     */
//    private String getMediaType(String fileType) {
//        String mediaType;
//        switch (fileType) {
//            case "pdf":
//                mediaType = "application/pdf";
//                break;
//            case "tif":
//                mediaType = "image/tiff";
//                break;
//            case "txt":
//                mediaType = "text/plain";
//                break;
//            case "rtf":
//                mediaType = "text/rtf";
//                break;
//            case "png":
//                mediaType = "image/png";
//                break;
//            case "gif":
//                mediaType = "image/gif";
//                break;
//            case "jpg":
//                mediaType = "image/jpeg";
//                break;
//            case "jpeg":
//                mediaType = "image/jpeg";
//                break;
//            case "doc":
//                mediaType = "application/msword";
//                break;
//            case "docx":
//                mediaType = "application/vnd.openxmlformats-officedocument.wordprocessingml.document";
//                break;
//            case "xsl":
//                mediaType = "application/vnd.ms-excel";
//                break;
//            case "xslx":
//                mediaType = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
//                break;
//            case "ppt":
//                mediaType = "application/vnd.ms-powerpoint";
//                break;
//            case "pptx":
//                mediaType = "application/vnd.openxmlformats-officedocument.presentationml.presentation";
//                break;
//            default:
//                mediaType = MediaType.WILDCARD;
//        }
//        return mediaType;
//    }
//}
