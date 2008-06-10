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

    ftw = new Image();
    ftw.addEventListener("load", paint, false);
    ftw.src = "/static/fadetowhite.png";
    YAHOO.util.Event.addListener(canvas, "click", animateCovers);
}
YAHOO.util.Event.onDOMReady(initCanvas);

/* helper function to simplify animation */
function putCover(ctx, img, n) {
    var width = ctx.canvas.getAttribute("width");
    var height = ctx.canvas.getAttribute("height");

    // figure out the correct x, y and size
    var x = width/2;
    var y = height/2;
    var size;
    if (Math.abs(n) < 1) {
	x += n*160;
	y -= Math.abs(n)*20;
	size = 160 - Math.abs(n)*40;
    } else {
	if (n < 0)
	    x += n*140-20;
	else
	    x += n*140+20;
	y -= 20;
	size = 120;
    }

    x = Math.round(x-size/2); // left
    y = Math.round(y); // bottom
    size = Math.round(size);
    /*
    if (img.canvasSize == size &&
	img.canvasX >= 0 &&
	img.canvasX <= width-size) {
	// reuse old
	if (img.canvasX == x && img.canvasY == y)
	    return;
	ctx.drawImage(ctx.canvas, img.canvasX, img.canvasY-size, size, size*2,
		      x, y-size, size, size*2);
	return;
    }
    */
    // draw cover
    ctx.drawImage(img, x, y-size, size, size);
    // draw reflection
    ctx.save();
    ctx.translate(0, y);
    ctx.scale(1, -1);
    ctx.translate(0, -y);
    ctx.drawImage(img, x, y-size, size, size);
    ctx.restore();
    // fade reflection to white
    ctx.drawImage(ftw, x, y, size, size);
    /*
    img.canvasX = x;
    img.canvasY = y;
    img.canvasSize = size;
    */
}

function paint() {
    var canvas = document.getElementById("coverflow");
    var width = canvas.getAttribute("width");
    var height = canvas.getAttribute("height");
    var ctx = canvas.getContext("2d");

    ctx.clearRect(0, 0, width, height);

    for (var i = 0; i < coverImages.length; i++) {
	if (Math.abs(i-coverOffset) < 3)
	    putCover(ctx, coverImages[i], i-coverOffset, width/2, height/2);
    }
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
