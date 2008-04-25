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

var coverImages = [];

var ftw;

function init() {
    canvas = document.getElementById("canvas");
    canvas.setAttribute("width", 1000);
    canvas.setAttribute("height", 600);

    loadCovers();

    ftw = new Image();
    //ftw.addEventListener("load", alert, false);
    ftw.src = "fadetowhite.png";
}

function loadCovers() {
    for (var i = 0; i < coverURLs.length; i++) {
	var im = new Image();
	im.src = coverURLs[i];
	im.addEventListener("load", paint, false);
	coverImages[i] = im;
    }
}

/* context ctx
 * centerline x
 * baseline y
 * maximum width/height size
 */
function putCover(ctx, img, x, y, size) {
    ctx.save();
    ctx.translate(x, -y);
    // draw cover
    ctx.drawImage(img, -size/2, -size, size, size);
    ctx.save();
    ctx.scale(1, -1);
    ctx.globalAlpha = 0.2;
    // draw reflection
    ctx.drawImage(img, -size/2, -size, size, size);
    ctx.restore();
    // fade reflection to white
    ctx.drawImage(ftw, -size/2, 0, size, size);
    ctx.restore();
}

function paint() {
    var canvas = document.getElementById("canvas");
    var width = canvas.getAttribute("width");
    var height = canvas.getAttribute("height");
    var ctx = canvas.getContext("2d");

    ctx.clearRect(0, 0, width, height);

    ctx.save();
    ctx.translate(width/2, height/2);
    putCover(ctx, coverImages[0], -400, 20, 150);
    putCover(ctx, coverImages[1], -200, 20, 150);
    putCover(ctx, coverImages[2], 0, 0, 200);
    putCover(ctx, coverImages[3], 200, 20, 150);
    putCover(ctx, coverImages[4], 400, 20, 150);
    ctx.restore();
}
