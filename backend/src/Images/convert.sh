
WORK_DIR=$(cd $(dirname $0) && pwd)

DATE=$(date +"%s")
cd "${WORK_DIR}"
#b
if [ $# -eq 0 ]; then
	BRANCH="${DATE}_default"
	MSG="default"
else 
	webp="$1"
	jpg="$2"
fi

if [ $# -eq 3 ]; then
	WORK_DIR="$3"
fi

pushd ${WORK_DIR}
convert ${webp} ${jpg}
popd
exit 0
