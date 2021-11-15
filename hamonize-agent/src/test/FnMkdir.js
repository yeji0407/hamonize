
function FnHwInfoMkdir() {

	try {
		fs.lstatSync("/etc/hamonize/hwinfo").isDirectory();
	} catch (e) {
		if (e.code == 'ENOENT') {
			var exec = require('child_process').exec;
			exec(" sudo mkdir /etc/hamonize/hwinfo/ && sudo touch /etc/hamonize/hwinfo/hwinfo.hm ",
				function (err, stdout, stderr) {
					if (err !== null) {
						return 0;
					}
					else {
						return 1;
					}
				});
		}
	}
	return 1;
}

function FnProgrmMkdir() {

	try {
		fs.lstatSync("/etc/hamonize/progrm").isDirectory();
	} catch (e) {
		if (e.code == 'ENOENT') {
			var exec = require('child_process').exec;
			exec(" sudo mkdir /etc/hamonize/progrm/ && sudo touch /etc/hamonize/progrm/progrm.hm ",
				function (err, stdout, stderr) {
					if (err !== null) {
						return 0;
					}
				});
		}
	}
	return 1;
}


module.exports = {
	FnHwInfoMkdir,
	FnProgrmMkdir
}