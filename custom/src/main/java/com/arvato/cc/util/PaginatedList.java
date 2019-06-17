package com.arvato.cc.util;

import java.util.List;

/**
 *
 * @author Justin
 */
public class PaginatedList<T> {

    public final static String DIR_ASC = "asc";
    public final static String DIR_DESC = "desc";

    private List<T> list;
    private int pageNumber;
    private int objectsPerPage;
    private int fullListSize;
    private String sortCriterion;
    private String sortDirection;
    private String searchId;
    private String groupField;

    public PaginatedList() {
    }

    public PaginatedList(int pageNumber, int objectsPerPage, String sortCriterion, String sortDirection) {
        this.pageNumber = pageNumber;
        this.objectsPerPage = objectsPerPage;
        this.sortCriterion = sortCriterion;
        this.sortDirection = sortDirection;
    }

    public List<T> getList() {
        return list;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public int getObjectsPerPage() {
        return objectsPerPage;
    }

    public int getFullListSize() {
        return fullListSize;
    }

    public String getSortCriterion() {
        return sortCriterion;
    }

    public String getSortDirection() {
        return sortDirection;
    }

    public String getSearchId() {
        return searchId;
    }

    /**
     * @param list the list to set
     */
    public void setList(List<T> list) {
        this.list = list;
    }

    /**
     * @param pageNumber the pageNumber to set
     */
    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    /**
     * @param objectsPerPage the objectsPerPage to set
     */
    public void setObjectsPerPage(int objectsPerPage) {
        this.objectsPerPage = objectsPerPage;
    }

    /**
     * @param fullListSize the fullListSize to set
     */
    public void setFullListSize(int fullListSize) {
        this.fullListSize = fullListSize;
    }

    /**
     * @param sortCriterion the sortCriterion to set
     */
    public void setSortCriterion(String sortCriterion) {
        this.sortCriterion = sortCriterion;
    }

    /**
     * @param sortDirection the sortDirection to set
     */
    public void setSortDirection(String sortDirection) {
        this.sortDirection = sortDirection;
    }

    /**
     * @param searchId the searchId to set
     */
    public void setSearchId(String searchId) {
        this.searchId = searchId;
    }

    public int getStartIndex() {
        return (pageNumber - 1) * objectsPerPage;
    }

    
    public String getGroupField()
    {
        return groupField;
    }

    
    public void setGroupField(String groupField)
    {
        this.groupField = groupField;
    }
    
    
}
