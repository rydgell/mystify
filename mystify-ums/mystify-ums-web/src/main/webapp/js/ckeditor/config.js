/**
 * @license Copyright (c) 2003-2015, CKSource - Frederico Knabben. All rights reserved.
 * For licensing, see LICENSE.md or http://ckeditor.com/license
 */

CKEDITOR.editorConfig = function( config ) {
	// Define changes to default configuration here. For example:
	// config.language = 'fr';
	// config.uiColor = '#AADC6E';
	/*config.toolbar = [[ 'Cut', 'Copy', 'Paste',  '-', 'Undo', 'Redo', 'Bold', 'Italic',"Image","Format","FontSize","TextColor" ,"Link" ,"Unlink","CodeSnippet","basicstyles","font"]]*/
	//config.filebrowserUploadUrl="${ROOT}/upload/uploadImage.do";
	
	/*config.toolbar =     
		[     
		    ['Source','-','Save','NewPage','Preview','-','Templates'],     
		    ['Cut','Copy','Paste','PasteText','PasteFromWord','-','Print', 'SpellChecker', 'Scayt'],     
		    ['Undo','Redo','-','Find','Replace','-','SelectAll','RemoveFormat'],     
		    ['Form', 'Checkbox', 'Radio', 'TextField', 'Textarea', 'Select', 'Button', 'ImageButton', 'HiddenField'],     
		    ['BidiLtr', 'BidiRtl'],     
		    '/',     
		    ['Bold','Italic','Underline','Strike','-','Subscript','Superscript'],     
		    ['NumberedList','BulletedList','-','Outdent','Indent','Blockquote','CreateDiv'],     
		    ['JustifyLeft','JustifyCenter','JustifyRight','JustifyBlock'],     
		    ['Link','Unlink','Anchor'],     
		    ['Image','Flash','Table','HorizontalRule','Smiley','SpecialChar','PageBreak'],     
		    '/',     
		    ['Styles','Format','Font','FontSize'],     
		    ['TextColor','BGColor'],     
		    ['Maximize', 'ShowBlocks','-','About']     
		]; */
	
	
	config.toolbar =     
	[     
	    ['Source','-','Preview','-','Templates','Undo','Redo','-','Maximize', 'ShowBlocks'],       
	    ['TextColor','BGColor'],        
	    ['Styles','Format','Font','FontSize'],     
	    '/',   
	    ['Bold','Italic','Underline','Strike','-','Subscript','Superscript'],     
	    ['NumberedList','BulletedList','-','Outdent','Indent','Blockquote','CreateDiv'],     
	    ['JustifyLeft','JustifyCenter','JustifyRight','JustifyBlock'],     
	    ['Link','Unlink','Anchor'],     
	    ['Image','Table','HorizontalRule','Smiley','SpecialChar'],     
	];  
 
};
