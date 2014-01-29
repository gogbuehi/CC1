/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cc2.IDEs.PHP;

/**
 *
 * @author goodwin
 */
public class PhpSerializer {
    public static final String[] CODE_ENCAPSULATION_SCRIPT={"<?php","?>","<?","?>"};
    public static final String[] CODE_ENCAPSULATION_BRACKETS={"{","}"};
    public static final String[] CODE_ENCAPSULATION_PARENTHESES={"(",")"};
    public static final String[] CODE_ENCAPSULATION_COMMENT={"/*","*/"};
    public static final String[] CODE_ENCAPSULATION_JAVADOC={"/**","*/"};
    public static final String[] CODE_ENCAPSULATION_QUOTES={"\"","\"","'","'"};
    public static final String SPECIAL_CHARACTER_VARIABLE="$";
    public static final String SPECIAL_CHARACTER_REFERENCE="&";
    public static final String SPECIAL_CHARACTER_STATEMENT_END=";";
    public static final String SPECIAL_CHARACTER_CONCATENATE=".";
    public static final String[] SPECIAL_CHARACTERS_LOGIC={"&","|","<",">","!","="};
    public static final String[] BRACKET_KEYWORDS={"class","else","try"};
    public static final String[] PARENTHESES_BRACKET_KEYWORDS={"function","for","foreach","while","if","else if","switch","catch"};
    public static final String[] OPEN_KEYWORDS={"public","private","protected","static","new","const","require","require_once","include","include_once"};
    
    
    public PhpSerializer() {
        
    }
    
    public void deserialize() {
        String testPhpDoc = "<?php\n"+
            "require_once 'includes/base/HtmlPage.php';\n"+
            "require_once 'includes/modules/MainMenu.php';\n"+
            "require_once 'includes/modules/Profile.php';\n"+
            "require_once 'inc/data/Page.php';\n"+
            "require_once 'inc/data/associations/Assoc_Slideshow_Image.php';\n"+
            "/**\n"+
            " * Description of ContentPage\n"+
            " *\n"+
            " * @author goodwin\n"+
            " */\n"+
            "class ContentPage extends HtmlPage {\n"+
            "    protected $headerNode;\n"+
            "    protected $footerNode;\n"+
            "    protected $sectionNode;\n"+
            "    function  __construct($title) {\n"+
            "        parent::__construct($title);\n"+
            "        \n"+
            "        $this->headerNode = new ContentNode('header');\n"+
            "        $this->footerNode = new ContentNode('footer');\n"+
            "        $this->sectionNode = new ContentNode('section');\n"+
            "        $this->sectionNode->addClass('new');\n"+
            "        $this->sectionNode->addClass('main');\n"+
            "        $this->sectionNode->addClass('color_ctrl');\n"+
            "        $resultsSection = new ContentNode('section');\n"+
            "        $resultsSection->addClass('results');\n"+
            "\n"+
            "        $this->addContent($this->headerNode);\n"+
            "        $this->addContent($this->sectionNode);\n"+
            "        $this->addContent($resultsSection);\n"+
            "        $this->addContent($this->footerNode);\n"+
            "        \n"+
            "        //Add Styles\n"+
            "        $this->addStyle('/css/ui-lightness/jquery-ui-1.8.6.custom.css');\n"+
            "        $this->addStyle('/css/jquery.mobile-1.0.a2.min.css');\n"+
            "        $this->addStyle('/css/ballantines.css');\n"+
            "        $this->addStyle('/css/bpr_colors.css');\n"+
            "\n"+
            "        //Add Scripts\n"+
            "        $this->addScript('/js/jquery-1.4.2.js');\n"+
            "        $this->addScript('/js/jquery-ui-1.8.6.custom.min.js');\n"+
            "        //$this->addScript('/js/jquery.mobile-1.0a2.min.js');\n"+
            "        $this->addScript('/js/modernizr-1.5.min.js');\n"+
            "        $this->addScript('/js/URI.js');\n"+
            "        $this->addScript('/js/engine.js?1');\n"+
            "        $this->addScript('http://fast.fonts.com/jsapi/c1432989-bfcd-4fc8-bd39-1c64b3055eec.js');\n"+
            "\n"+
            "        //Header Content\n"+
            "        $h1 = new ContentNode('h1');\n"+
            "        $img = new ContentNode('img');\n"+
            "        $img->setAttribute('title', 'Ballatines[pr]');\n"+
            "        $img->setAttribute('src', '/images/bpr_top_bar_1024x178.png');\n"+
            "        $h1->addNode($img);\n"+
            "\n"+
            "        $mainMenu = new MainMenu();\n"+
            "        $mainMenu->addClass('mainmenu');\n"+
            "\n"+
            "        $this->headerNode->addNode($h1);\n"+
            "        $this->headerNode->addNode($mainMenu);\n"+
            "        \n"+
            "        $this->getPageContent();\n"+
            "\n"+
            "    }\n"+
            "    \n"+
            "    function getPageContent() {\n"+
            "        if ($_SERVER['REQUEST_URI'] == '/') {\n"+
            "            \n"+
            "            return;\n"+
            "        }\n"+
            "        $page = new Page(false);\n"+
            "        if($page->hasRecord($_SERVER['REQUEST_URI'],'uri')) {\n"+
            "            //TODO Bring in proper content\n"+
            "            $page = new Page($_SERVER['REQUEST_URI'],'uri');\n"+
            "            //TODO Set the Page Title; $page->getValueOf('title')\n"+
            "            $this->sectionNode->setAttribute('id', 'section_'.$page->getIdValue());\n"+
            "            switch($_SERVER['REQUEST_URI']) {\n"+
            "                case '/home':\n"+
            "                    $this->getHomePageContent();\n"+
            "                    break;\n"+
            "                case '/the_team':\n"+
            "                    $this->getTheTeamPageContent();\n"+
            "                    break;\n"+
            "                default:\n"+
            "                    \n"+
            "            }\n"+
            "        } else {\n"+
            "            //Return 404\n"+
            "            //header(\"HTTP/1.0 404 Not Found\");\n"+
            "            $h1 = new ContentNode('h1');\n"+
            "            $h1->addText('404: Page Not Found for: '.$_SERVER['REQUEST_URI']);\n"+
            "            $this->sectionNode->addNode($h1);\n"+
            "        }\n"+
            "        \n"+
            "        \n"+
            "    }\n"+
            "    \n"+
            "    private function getHomePageContent() {\n"+
            "        $className = 'home_page';\n"+
            "        $pageCaption = 'Welcome to Ballantines[pr], a boutique public relations agency based in Los Angeles, with satellite offices in London, New York and Santa Fe. B[pr] specializes in a wide array of industries ranging from Architecture & Fashion to Entertainment & Technology.';\n"+
            "        $subheadText = 'Highlights';\n"+
            "        \n"+
            "        //Add Extra image modules\n"+
            "        $bottomLeftImage = '/images/page_1_exports2.jpg';\n"+
            "        $bottomRightImage = '/images/page_1_exports3.jpg';\n"+
            "        $span = new ContentNode('span');\n"+
            "        $span->addClass('lower_images');\n"+
            "        $img1 = new ContentNode('img');\n"+
            "        $img1->setAttribute('src',$bottomLeftImage);\n"+
            "        $img2 = new ContentNode('img');\n"+
            "        $img2->setAttribute('src',$bottomRightImage);\n"+
            "\n"+
            "        $span->addNode($img1);\n"+
            "        $span->addNode($img2);\n"+
            "        \n"+
            "        $slideshow = $this->getPhotoslideshow(1);\n"+
            "        \n"+
            "        $moduleItems = array(\n"+
            "            $slideshow, //Add Photo Module\n"+
            "            $span //Add Extra image modules\n"+
            "        );\n"+
            "        \n"+
            "        $jsTriggerArray = array('placeMenu');\n"+
            "        \n"+
            "        $this->buildBasePageContent($className, $pageCaption, $subheadText, $moduleItems, $jsTriggerArray);\n"+
            "    }\n"+
            "    private function getTheTeamPageContent() {\n"+
            "        $className = 'the_team_page';\n"+
            "        $pageCaption = 'The Team';\n"+
            "        $subheadText = 'Sarah Robarts';\n"+
            "        \n"+
            "        $moduleItems = array();\n"+
            "        \n"+
            "        $picSpan = new ContentNode('span');\n"+
            "        $picSpan->addClass('portrait');\n"+
            "        $profilePic = '/images/portraits/srobarts.jpg';\n"+
            "        $img = new ContentNode('img');\n"+
            "        $img->addClass('color_ctrl');\n"+
            "        $img->setAttribute('src',$profilePic);\n"+
            "        $picSpan->addNode($img);\n"+
            "        array_push($moduleItems,$picSpan);\n"+
            "        \n"+
            "        $textDiv = new ContentNode('div');\n"+
            "        $textDiv->addClass('portraitText');\n"+
            "        $textDiv->addClass('color_ctrl');\n"+
            "        $h3 = new ContentNode('h3');\n"+
            "        $h3->addClass('personnel');\n"+
            "        $h3->addText('Sarah Robarts');\n"+
            "        $textDiv->addNode($h3);\n"+
            "        \n"+
            "        $h4 = new ContentNode('h4');\n"+
            "        $h4->addClass('personnel color_ctrl');\n"+
            "        $h4->addText('president'); //Note, this should use capitals and be changed to lower case after\n"+
            "        $textDiv->addNode($h4);\n"+
            "        \n"+
            "        $textArray = array(\n"+
            "            'Sarah got her start in PR working in London for such companies as The Conran Shop, The Artisan Trust and Martha Stewart (The Wedding List). After moving to the U.S., she founded Ballantines PR in 2000. ',\n"+
            "            'She was brought up in Africa and has worked for organizations such as Oxfam in the U.K., on both the PR and creative design side. She has an MFA & B.A. (Hons) in fine art and studied in Canada and France. Sarah specializes in travel, hospitality, fashion, film and related industries, as well as image positioning for clients. Speaks English/French and Swahili.',\n"+
            "            'Sarah is the mother of two children, and she is based in Los Angeles. ',\n"+
            "            'See Sarah on Extra! discussing Hotel Casa del Mar.'\n"+
            "        );\n"+
            "        \n"+
            "        foreach($textArray as $key => $value) {\n"+
            "            $textDiv->addNode($this->paragraphText($value));\n"+
            "        }\n"+
            "        array_push($moduleItems,$textDiv);\n"+
            "        \n"+
            "        $clearDiv = new ContentNode('div');\n"+
            "        $clearDiv->addClass('clear');\n"+
            "        \n"+
            "        array_push($moduleItems,$clearDiv);\n"+
            "        \n"+
            "        //$this->buildBasePageContent($className, $pageCaption, $subheadText, $moduleItems, $jsTriggerArray);\n"+
            "        \n"+
            "        $profile = new Profile(1);\n"+
            "        \n"+
            "        //Add Header Text\n"+
            "        $h2 = new ContentNode('h2');\n"+
            "        $h2->addText($subheadText);\n"+
            "        \n"+
            "        //Add Caption\n"+
            "        $text = new ContentNode('p');\n"+
            "        $text->addClass('page_caption');\n"+
            "        $text->addText($pageCaption);\n"+
            "        \n"+
            "        $profile->addNode($h2);\n"+
            "        $profile->addNode($text);\n"+
            "        $profile->addNode($picSpan);\n"+
            "        $profile->addNode($textDiv);\n"+
            "        $profile->addNode($clearDiv);\n"+
            "        \n"+
            "        $this->sectionNode->addNode($profile);\n"+
            "    }\n"+
            "    \n"+
            "    private function buildBasePageContent(\n"+
            "            $className,\n"+
            "            $pageCaption,\n"+
            "            $subheadText,\n"+
            "            $moduleItems = array(),\n"+
            "            $jsTriggerArray=array()\n"+
            "     ) {\n"+
            "        //Add Caption\n"+
            "        $text = new ContentNode('p');\n"+
            "        $text->addClass('page_caption');\n"+
            "        $text->addText($pageCaption);\n"+
            "        $this->sectionNode->addNode($text);\n"+
            "        \n"+
            "        //Add Header Text\n"+
            "        $h2 = new ContentNode('h2');\n"+
            "        $h2->addText($subheadText);\n"+
            "        $this->sectionNode->addNode($h2);\n"+
            "        \n"+
            "        //Add Module\n"+
            "        $module = new ContentNode('div');\n"+
            "        $module->addClass($className);\n"+
            "        $module->addClass('module color_ctrl');\n"+
            "        foreach ($moduleItems as $key => &$value) {\n"+
            "            $module->addNode($value);\n"+
            "        }\n"+
            "        $this->sectionNode->addNode($module);\n"+
            "        \n"+
            "        //TODO: Make mechanism for default triggering (i.e. removing menu)\n"+
            "        \n"+
            "        //Add JS Trigger module\n"+
            "        //TODO: Create module to indicate to JS what it should do\n"+
            "        //i.e. Bring menu in\n"+
            "        $this->jsTriggers($jsTriggerArray);\n"+
            "        \n"+
            "    }\n"+
            "    function &getPhotoslideshow($id) {\n"+
            "        //$slideshow = new Slideshow($id);\n"+
            "        \n"+
            "        $slideshow = new Assoc_Slideshow_Image($id, 'slideshow_id');\n"+
            "        $div= new ContentNode('div');\n"+
            "        $div->addClass('photo_module');\n"+
            "        $div->setAttribute('id','photo_module_'.$id);\n"+
            "\n"+
            "        $ul = new ContentNode('ul');\n"+
            "        $ul->addClass('images');\n"+
            "        do {\n"+
            "            $ul->addNode($this->photoModuleImage($slideshow->content->getUri()));\n"+
            "        } while ($slideshow->loadNextRecord());\n"+
            "        $div->addNode($ul);\n"+
            "        return $div;\n"+
            "    }\n"+
            "\n"+
            "    function &photoModuleImage($imgUrl) {\n"+
            "        $img = new ContentNode('img');\n"+
            "        $img->setAttribute('src', $imgUrl);\n"+
            "        $li = new Contentnode('li');\n"+
            "        $li->addNode($img);\n"+
            "        return $li;\n"+
            "    }\n"+
            "    \n"+
            "    function &paragraphText($text) {\n"+
            "        $p = new ContentNode('p');\n"+
            "        $p->addText($text);\n"+
            "        return $p;\n"+
            "    }\n"+
            "    \n"+
            "    function jsTriggers($eventArray) {\n"+
            "        $form = new ContentNode('form');\n"+
            "        foreach ($eventArray as $key => $value) {\n"+
            "            $form->addNode($this->jsTriggerField($value));\n"+
            "        }\n"+
            "        $this->footerNode->addNode($form);\n"+
            "    }\n"+
            "    function &jsTriggerField($eventString) {\n"+
            "        $input = new ContentNode('input');\n"+
            "        $input->setAttribute('type','text');\n"+
            "        $input->setAttribute('value',$eventString);\n"+
            "        return $input;\n"+
            "    }\n"+
            "}\n"+
            "\n"+
            "?>";
        //Set LookupItems
        LookupItem phpCode = new LookupItem(CODE_ENCAPSULATION_SCRIPT[0]);
        LookupItem quotes = new LookupItem(CODE_ENCAPSULATION_QUOTES[0]);
        
        
        
        //Grab PHP Sections
        int encapsulationStart = testPhpDoc.indexOf(CODE_ENCAPSULATION_SCRIPT[0]);
        int encapsluationEnd = testPhpDoc.indexOf(CODE_ENCAPSULATION_SCRIPT[1]);
        
    }

}
