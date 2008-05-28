/*
var coverURLs = ["http://upload.wikimedia.org/wikipedia/en/a/a4/PleasePleaseMe.jpg",
		 "http://upload.wikimedia.org/wikipedia/en/0/0a/Withthebeatlescover.jpg",
		 "http://upload.wikimedia.org/wikipedia/en/e/e6/HardDayUK.jpg",
		 "http://upload.wikimedia.org/wikipedia/en/4/40/Beatlesforsale.jpg",
		 "http://upload.wikimedia.org/wikipedia/en/1/1e/HelpUK.jpg",
		 "http://upload.wikimedia.org/wikipedia/en/7/73/RubberSoulUK.jpg",
		 "http://upload.wikimedia.org/wikipedia/en/1/16/Revolver.jpg",
		 "http://upload.wikimedia.org/wikipedia/en/6/67/Pepper's.jpg",
		 "http://upload.wikimedia.org/wikipedia/en/4/4c/The_BEATLES_Cover_Art_Remastered.png",
		 "http://upload.wikimedia.org/wikipedia/en/a/ac/TheBeatles-YellowSubmarinealbumcover.jpg",
		 "http://upload.wikimedia.org/wikipedia/en/4/42/Beatles_-_Abbey_Road.jpg",
		 "http://upload.wikimedia.org/wikipedia/en/2/25/LetItBe.jpg"];
*/
var coverURLs = ["http://upload.wikimedia.org/wikipedia/en/thumb/a/a4/PleasePleaseMe.jpg/200px-PleasePleaseMe.jpg",
		 "http://upload.wikimedia.org/wikipedia/en/thumb/0/0a/Withthebeatlescover.jpg/200px-Withthebeatlescover.jpg",
		 "http://upload.wikimedia.org/wikipedia/en/thumb/e/e6/HardDayUK.jpg/200px-HardDayUK.jpg",
		 "http://upload.wikimedia.org/wikipedia/en/4/40/Beatlesforsale.jpg",
		 "http://upload.wikimedia.org/wikipedia/en/1/1e/HelpUK.jpg",
		 "http://upload.wikimedia.org/wikipedia/en/thumb/7/73/RubberSoulUK.jpg/200px-RubberSoulUK.jpg",
		 "http://upload.wikimedia.org/wikipedia/en/thumb/1/16/Revolver.jpg/200px-Revolver.jpg",
		 "http://upload.wikimedia.org/wikipedia/en/thumb/6/67/Pepper's.jpg/200px-Pepper's.jpg",
		 "http://upload.wikimedia.org/wikipedia/commons/thumb/0/0e/The_White_Album.svg/200px-The_White_Album.svg.png",
		 "http://upload.wikimedia.org/wikipedia/en/thumb/a/ac/TheBeatles-YellowSubmarinealbumcover.jpg/200px-TheBeatles-YellowSubmarinealbumcover.jpg",
		 "http://upload.wikimedia.org/wikipedia/en/thumb/4/42/Beatles_-_Abbey_Road.jpg/200px-Beatles_-_Abbey_Road.jpg",
		 "http://upload.wikimedia.org/wikipedia/en/thumb/2/25/LetItBe.jpg/200px-LetItBe.jpg"];

var coverImages = [];
var coverOffset = 0;
var ftw;

function initCanvas() {
    canvas = document.getElementById("coverflow");
    canvas.setAttribute("width", 760);
    canvas.setAttribute("height", 340);

    // eliminate duplicate covers
    var covers = {};
    var imgs = YAHOO.util.Dom.getElementsByClassName("coverart");
    for (var i=0; i<imgs.length; i++) {
	covers[imgs[i].src] = imgs[i];
    }
    for (var x in covers) {
	covers[x].addEventListener("load", paint, false);
	coverImages[coverImages.length] = covers[x];
    }

    //loadCovers();

    ftw = new Image();
    ftw.addEventListener("load", paint, false);
    ftw.src = "/static/fadetowhite.png";
    YAHOO.util.Event.addListener(canvas, "click", animateCovers);
}
YAHOO.util.Event.onDOMReady(initCanvas);

function loadCovers() {
    

    for (var i = 0; i < coverURLs.length; i++) {
	var im = new Image();
	im.addEventListener("load", paint, false);
	im.src = coverURLs[i];
	coverImages[i] = im;
    }
}

/* context ctx
 * centerline x
 * baseline y
 * maximum width/height size
 */
function putCoverAt(ctx, img, x, y, size) {
    ctx.save();
    ctx.translate(x, -y);
    // draw cover
    ctx.drawImage(img, -size/2, -size, size, size);
    // draw reflection
    ctx.save();
    ctx.scale(1, -1);
    ctx.globalAlpha = 0.2;
    ctx.drawImage(img, -size/2, -size, size, size);
    ctx.restore();
    // fade reflection to white
    ctx.drawImage(ftw, -size/2, 0, size, size);
    ctx.restore();
}

/* helper function simplify animation */
function putCover(ctx, img, n) {
    // figure out the correct x, y and size
    var x;
    var y;
    var size;
    if (Math.abs(n) < 1) {
	x = n*160;
	y = Math.abs(n)*20;
	size = 160 - Math.abs(n)*40;
    } else {
	if (n < 0)
	    x = n*140-20;
	else
	    x = n*140+20;
	y = 20;
	size = 120;
    }

    putCoverAt(ctx, img, x, y, size);
}

function paint() {
    var canvas = document.getElementById("coverflow");
    var width = canvas.getAttribute("width");
    var height = canvas.getAttribute("height");
    var ctx = canvas.getContext("2d");

    ctx.clearRect(0, 0, width, height);

    ctx.save();
    ctx.translate(width/2, height/2);
    for (var i = 0; i < coverImages.length; i++) {
	if (Math.abs(i-coverOffset) < 3)
	    putCover(ctx, coverImages[i], i-coverOffset);
    }
    ctx.restore();
}

function animateCovers() {
    var startOffset = coverOffset;
    var start = new Date().getTime();
    function animate() {
	var elapsed = new Date().getTime() - start;
	if (elapsed < 500) {
	    coverOffset = startOffset + elapsed/500.0;
	    setTimeout(animate, 50);
	    paint();
	} else {
	    coverOffset = startOffset + 1;
	    paint();
	}
    }
    animate();
}
