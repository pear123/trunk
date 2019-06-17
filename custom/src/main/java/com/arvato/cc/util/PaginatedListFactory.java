package com.arvato.cc.util;

import org.apache.commons.lang.StringUtils;

import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Justin
 */
public class PaginatedListFactory {

    private int defaultPageNum;
    private int defaultPageSize;
    private String defaultDir;

    private String pagenumKey = "page";
    private String pagesizeKey = "limit";
    private String dirKey = "dir";
    private String sortKey = "sort";

    public PaginatedList getPaginatedList(HttpServletRequest request) {
        PaginatedList paginatedList = new PaginatedList(defaultPageNum, defaultPageSize, null, defaultDir);
        
        if (StringUtils.isNotEmpty(request.getParameter(pagenumKey))) {
            paginatedList.setPageNumber(Integer.valueOf(request.getParameter(pagenumKey).trim()).intValue());
        }

        if (StringUtils.isNotEmpty(request.getParameter(pagesizeKey))) {
            paginatedList.setObjectsPerPage(Integer.valueOf(request.getParameter(pagesizeKey).trim()).intValue());
        }

        if (StringUtils.isNotEmpty(request.getParameter(sortKey))) {
            paginatedList.setSortCriterion(request.getParameter(sortKey).trim());
        }

        if (StringUtils.isNotEmpty(request.getParameter(dirKey))) {
            paginatedList.setSortDirection(request.getParameter(dirKey).trim());
        }

        return paginatedList;
    }

    /**
     * @param defaultPageNum the defaultPageNum to set
     */
    public void setDefaultPageNum(int defaultPageNum) {
        this.defaultPageNum = defaultPageNum;
    }

    /**
     * @param defaultPageSize the defaultPageSize to set
     */
    public void setDefaultPageSize(int defaultPageSize) {
        this.defaultPageSize = defaultPageSize;
    }

    /**
     * @param defaultDir the defaultDir to set
     */
    public void setDefaultDir(String defaultDir) {
        this.defaultDir = defaultDir;
    }

    /**
     * @param pagenumKey the pagenumKey to set
     */
    public void setPagenumKey(String pagenumKey) {
        this.pagenumKey = pagenumKey;
    }

    /**
     * @param pagesizeKey the pagesizeKey to set
     */
    public void setPagesizeKey(String pagesizeKey) {
        this.pagesizeKey = pagesizeKey;
    }

    /**
     * @param dirKey the dirKey to set
     */
    public void setDirKey(String dirKey) {
        this.dirKey = dirKey;
    }

    /**
     * @param sortKey the sortKey to set
     */
    public void setSortKey(String sortKey) {
        this.sortKey = sortKey;
    }
}
