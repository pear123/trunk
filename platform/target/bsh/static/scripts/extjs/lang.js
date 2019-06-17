/**
 * Copyright (c) 2000-2010 arvato systems (Shanghai)
 *
 * $Id: lang.js 2011-6-9 17:49:41 Justin $
 */
Ext.onReady(function(){
    if(Ext.Updater){
        Ext.Updater.defaults.indicatorText = messages['Ext.Updater.defaults.indicatorText'];
    }

    if(Ext.view.View){
        Ext.view.View.prototype.emptyText = messages['Ext.view.View.prototype.emptyText'];
    }

    if(Ext.grid.Panel){
        Ext.grid.Panel.prototype.ddText = messages['Ext.grid.Panel.prototype.ddText'];
    }

    if(Ext.TabPanelItem){
        Ext.TabPanelItem.prototype.closeText = messages['Ext.TabPanelItem.prototype.closeText'];
    }

    if(Ext.form.field.Base){
        Ext.form.field.Base.prototype.invalidText = messages['Ext.form.field.Base.prototype.invalidText'];
    }

    if (Ext.LoadMask) {
        Ext.LoadMask.prototype.msg = messages['Ext.LoadMask.prototype.msg'];
    }

    if(Ext.Date){
        Ext.Date.monthNames = [
        messages['Ext.Date.monthNames.jan'],
        messages['Ext.Date.monthNames.feb'],
        messages['Ext.Date.monthNames.mar'],
        messages['Ext.Date.monthNames.apr'],
        messages['Ext.Date.monthNames.may'],
        messages['Ext.Date.monthNames.june'],
        messages['Ext.Date.monthNames.july'],
        messages['Ext.Date.monthNames.aug'],
        messages['Ext.Date.monthNames.sept'],
        messages['Ext.Date.monthNames.oct'],
        messages['Ext.Date.monthNames.nov'],
        messages['Ext.Date.monthNames.dec']
        ];

        Ext.Date.dayNames = [
        messages['Ext.Date.dayNames.sun'],
        messages['Ext.Date.dayNames.mon'],
        messages['Ext.Date.dayNames.tue'],
        messages['Ext.Date.dayNames.wed'],
        messages['Ext.Date.dayNames.thu'],
        messages['Ext.Date.dayNames.fri'],
        messages['Ext.Date.dayNames.sat']
        ];

        Ext.Date.formatCodes.a = messages['Ext.Date.formatCodes.a'];
        Ext.Date.formatCodes.A = messages['Ext.Date.formatCodes.A'];
    }

    if(Ext.MessageBox){
        Ext.MessageBox.buttonText = {
            ok     : messages['Ext.MessageBox.buttonText.ok'],
            cancel : messages['Ext.MessageBox.buttonText.cancel'],
            yes    : messages['Ext.MessageBox.buttonText.yes'],
            no     : messages['Ext.MessageBox.buttonText.no']
        };
    }

    if(Ext.util.Format){
        Ext.apply(Ext.util.Format, {
            thousandSeparator: messages['Ext.util.Format.thousandSeparator'],
            decimalSeparator: messages['Ext.util.Format.decimalSeparator'],
            currencySign: messages['Ext.util.Format.currencySign'],
            dateFormat: messages['Ext.util.Format.dateFormat']
        });
    }

    if(Ext.picker.Date){
        Ext.apply(Ext.picker.Date.prototype, {
            todayText         : messages['Ext.picker.Date.prototype.todayText'],
            minText           : messages['Ext.picker.Date.prototype.minText'],
            maxText           : messages['Ext.picker.Date.prototype.maxText'],
            disabledDaysText  : messages['Ext.picker.Date.prototype.disabledDaysText'],
            disabledDatesText : messages['Ext.picker.Date.prototype.disabledDatesText'],
            monthNames        : Ext.Date.monthNames,
            dayNames          : Ext.Date.dayNames,
            nextText          : messages['Ext.picker.Date.prototype.nextText'],
            prevText          : messages['Ext.picker.Date.prototype.prevText'],
            monthYearText     : messages['Ext.picker.Date.prototype.monthYearText'],
            todayTip          : messages['Ext.picker.Date.prototype.todayTip'],
            format            : messages['Ext.util.Format.dateFormat']
        });
    }

    if(Ext.picker.Month) {
        Ext.apply(Ext.picker.Month.prototype, {
            okText            : messages['Ext.picker.Month.prototype.okText'],
            cancelText        : messages['Ext.picker.Month.prototype.cancelText']
        });
    }

    if(Ext.toolbar.Paging){
        Ext.apply(Ext.PagingToolbar.prototype, {
            beforePageText : messages['Ext.PagingToolbar.prototype.beforePageText'],
            afterPageText  : messages['Ext.PagingToolbar.prototype.afterPageText'],
            firstText      : messages['Ext.PagingToolbar.prototype.firstText'],
            prevText       : messages['Ext.PagingToolbar.prototype.prevText'],
            nextText       : messages['Ext.PagingToolbar.prototype.nextText'],
            lastText       : messages['Ext.PagingToolbar.prototype.lastText'],
            refreshText    : messages['Ext.PagingToolbar.prototype.refreshText'],
            displayMsg     : messages['Ext.PagingToolbar.prototype.displayMsg'],
            emptyMsg       : messages['Ext.PagingToolbar.prototype.emptyMsg']
        });
    }

    if(Ext.form.field.Text){
        Ext.apply(Ext.form.field.Text.prototype, {
            minLengthText : messages['Ext.form.field.Text.prototype.minLengthText'],
            maxLengthText : messages['Ext.form.field.Text.prototype.maxLengthText'],
            blankText     : messages['Ext.form.field.Text.prototype.blankText'],
            regexText     : messages['Ext.form.field.Text.prototype.regexText'],
            emptyText     : null
        });
    }

    if(Ext.form.field.Number){
        Ext.apply(Ext.form.field.Number.prototype, {
            minText : messages['Ext.form.field.Number.prototype.minText'],
            maxText : messages['Ext.form.field.Number.prototype.maxText'],
            nanText : messages['Ext.form.field.Number.prototype.nanText']
        });
    }

    if(Ext.form.field.Date){
        Ext.apply(Ext.form.field.Date.prototype, {
            disabledDaysText  : messages['Ext.form.field.Date.prototype.disabledDaysText'],
            disabledDatesText : messages['Ext.form.field.Date.prototype.disabledDatesText'],
            minText           : messages['Ext.form.field.Date.prototype.minText'],
            maxText           : messages['Ext.form.field.Date.prototype.maxText'],
            invalidText       : messages['Ext.form.field.Date.prototype.invalidText'],
            format            : messages['Ext.util.Format.dateFormat']
        });
    }

    if(Ext.form.field.ComboBox){
        Ext.apply(Ext.form.field.ComboBox.prototype, {
            loadingText       : messages['Ext.form.field.ComboBox.prototype.loadingText'],
            valueNotFoundText : undefined // messages['Ext.form.field.ComboBox.prototype.valueNotFoundText']
        });
    }

    if(Ext.form.field.VTypes){
        Ext.apply(Ext.form.field.VTypes, {
            emailText    : messages['Ext.form.field.VTypes.emailText'],
            urlText      : messages['Ext.form.field.VTypes.urlText'],
            alphaText    : messages['Ext.form.field.VTypes.alphaText'],
            alphanumText : messages['Ext.form.field.VTypes.alphanumText']
        });
    }
    //add HTMLEditor's tips by andy_ghg
    if(Ext.form.field.HtmlEditor){
        Ext.apply(Ext.form.field.HtmlEditor.prototype, {
            createLinkText : messages['Ext.form.field.HtmlEditor.prototype.createLinkText'],
            buttonTips : {
                bold : {
                    title: messages['Ext.form.field.HtmlEditor.prototype.buttonTips.bold.title'],
                    text: messages['Ext.form.field.HtmlEditor.prototype.buttonTips.bold.text'],
                    cls: Ext.baseCSSPrefix + messages['Ext.form.field.HtmlEditor.prototype.buttonTips.bold.cls']
                },
                italic : {
                    title: messages['Ext.form.field.HtmlEditor.prototype.buttonTips.italic.title'],
                    text: messages['Ext.form.field.HtmlEditor.prototype.buttonTips.italic.text'],
                    cls: Ext.baseCSSPrefix + messages['Ext.form.field.HtmlEditor.prototype.buttonTips.italic.cls']
                },
                underline : {
                    title: messages['Ext.form.field.HtmlEditor.prototype.buttonTips.underline.title'],
                    text: messages['Ext.form.field.HtmlEditor.prototype.buttonTips.underline.text'],
                    cls: Ext.baseCSSPrefix + messages['Ext.form.field.HtmlEditor.prototype.buttonTips.underline.cls']
                },
                increasefontsize : {
                    title: messages['Ext.form.field.HtmlEditor.prototype.buttonTips.increasefontsize.title'],
                    text: messages['Ext.form.field.HtmlEditor.prototype.buttonTips.increasefontsize.text'],
                    cls: Ext.baseCSSPrefix + messages['Ext.form.field.HtmlEditor.prototype.buttonTips.increasefontsize.cls']
                },
                decreasefontsize : {
                    title: messages['Ext.form.field.HtmlEditor.prototype.buttonTips.decreasefontsize.title'],
                    text: messages['Ext.form.field.HtmlEditor.prototype.buttonTips.decreasefontsize.text'],
                    cls: Ext.baseCSSPrefix + messages['Ext.form.field.HtmlEditor.prototype.buttonTips.decreasefontsize.cls']
                },
                backcolor : {
                    title: messages['Ext.form.field.HtmlEditor.prototype.buttonTips.backcolor.title'],
                    text: messages['Ext.form.field.HtmlEditor.prototype.buttonTips.backcolor.text'],
                    cls: Ext.baseCSSPrefix + messages['Ext.form.field.HtmlEditor.prototype.buttonTips.backcolor.cls']
                },
                forecolor : {
                    title: messages['Ext.form.field.HtmlEditor.prototype.buttonTips.forecolor.title'],
                    text: messages['Ext.form.field.HtmlEditor.prototype.buttonTips.forecolor.text'],
                    cls: Ext.baseCSSPrefix + messages['Ext.form.field.HtmlEditor.prototype.buttonTips.forecolor.cls']
                },
                justifyleft : {
                    title: messages['Ext.form.field.HtmlEditor.prototype.buttonTips.justifyleft.title'],
                    text: messages['Ext.form.field.HtmlEditor.prototype.buttonTips.justifyleft.text'],
                    cls: Ext.baseCSSPrefix + messages['Ext.form.field.HtmlEditor.prototype.buttonTips.justifyleft.cls']
                },
                justifycenter : {
                    title: messages['Ext.form.field.HtmlEditor.prototype.buttonTips.justifycenter.title'],
                    text: messages['Ext.form.field.HtmlEditor.prototype.buttonTips.justifycenter.text'],
                    cls: Ext.baseCSSPrefix + messages['Ext.form.field.HtmlEditor.prototype.buttonTips.justifycenter.cls']
                },
                justifyright : {
                    title: messages['Ext.form.field.HtmlEditor.prototype.buttonTips.justifyright.title'],
                    text: messages['Ext.form.field.HtmlEditor.prototype.buttonTips.justifyright.text'],
                    cls: Ext.baseCSSPrefix + messages['Ext.form.field.HtmlEditor.prototype.buttonTips.justifyright.cls']
                },
                insertunorderedlist : {
                    title: messages['Ext.form.field.HtmlEditor.prototype.buttonTips.insertunorderedlist.title'],
                    text: messages['Ext.form.field.HtmlEditor.prototype.buttonTips.insertunorderedlist.text'],
                    cls: Ext.baseCSSPrefix + messages['Ext.form.field.HtmlEditor.prototype.buttonTips.insertunorderedlist.cls']
                },
                insertorderedlist : {
                    title: messages['Ext.form.field.HtmlEditor.prototype.buttonTips.insertorderedlist.title'],
                    text: messages['Ext.form.field.HtmlEditor.prototype.buttonTips.insertorderedlist.text'],
                    cls: Ext.baseCSSPrefix + messages['Ext.form.field.HtmlEditor.prototype.buttonTips.insertorderedlist.cls']
                },
                createlink : {
                    title: messages['Ext.form.field.HtmlEditor.prototype.buttonTips.createlink.title'],
                    text: messages['Ext.form.field.HtmlEditor.prototype.buttonTips.createlink.text'],
                    cls: Ext.baseCSSPrefix + messages['Ext.form.field.HtmlEditor.prototype.buttonTips.createlink.cls']
                },
                sourceedit : {
                    title: messages['Ext.form.field.HtmlEditor.prototype.buttonTips.sourceedit.title'],
                    text: messages['Ext.form.field.HtmlEditor.prototype.buttonTips.sourceedit.text'],
                    cls: Ext.baseCSSPrefix + messages['Ext.form.field.HtmlEditor.prototype.buttonTips.sourceedit.cls']
                }
            }
        });
    }


    if(Ext.grid.header.Container){
        Ext.apply(Ext.grid.header.Container.prototype, {
            sortAscText  : messages['Ext.grid.header.Container.prototype.sortAscText'],
            sortDescText : messages['Ext.grid.header.Container.prototype.sortDescText'],
            lockText     : messages['Ext.grid.header.Container.prototype.lockText'],
            unlockText   : messages['Ext.grid.header.Container.prototype.unlockText'],
            columnsText  : messages['Ext.grid.header.Container.prototype.columnsText']
        });
    }

    if(Ext.grid.PropertyColumnModel){
        Ext.apply(Ext.grid.PropertyColumnModel.prototype, {
            nameText   : messages['Ext.grid.PropertyColumnModel.prototype.nameText'],
            valueText  : messages['Ext.grid.PropertyColumnModel.prototype.valueText'],
            dateFormat : messages['Ext.util.Format.dateFormat']
        });
    }

    if(Ext.layout.BorderLayout && Ext.layout.BorderLayout.SplitRegion){
        Ext.apply(Ext.layout.BorderLayout.SplitRegion.prototype, {
            splitTip : messages['Ext.layout.BorderLayout.SplitRegion.prototype.splitTip'],
            collapsibleSplitTip : messages['Ext.layout.BorderLayout.SplitRegion.prototype.collapsibleSplitTip']
        });
    }    
    
    if (Ext.grid.RowEditor) {
        Ext.grid.RowEditor.prototype.saveBtnText = messages['Ext.grid.RowEditor.prototype.saveBtnText'];
        Ext.grid.RowEditor.prototype.cancelBtnText =  messages['Ext.grid.RowEditor.prototype.cancelBtnText'];
        Ext.grid.RowEditor.prototype.errorsTextBtnText = messages['Ext.grid.RowEditor.prototype.errorsTextBtnText'];
        Ext.grid.RowEditor.prototype.dirtyText = messages['Ext.grid.RowEditor.prototype.dirtyText'];
    }
});
